package com.task.minitweet.services.impl;

import com.task.minitweet.domains.dtos.auth.RegisterUserDto;
import com.task.minitweet.domains.dtos.user.UpdateRolesDto;
import com.task.minitweet.domains.enums.RoleActions;
import com.task.minitweet.domains.models.Token;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.TokenRepository;
import com.task.minitweet.repositories.UserRepository;
import com.task.minitweet.services.contract.RoleService;
import com.task.minitweet.services.contract.UserService;
import com.task.minitweet.utils.JWTTools;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTTools jwtTools;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public UserServiceImpl(UserRepository userRepository, JWTTools jwtTools, TokenRepository tokenRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.jwtTools = jwtTools;
        this.tokenRepository = tokenRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    /**
     * Este método registra un nuevo usuario.
     *
     * @param userDto El DTO del usuario a registrar.
     * @throws HttpError Si el usuario ya existe.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void registerUser(RegisterUserDto userDto) {
        try{
            User user = userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
            if(user != null){
                throw new HttpError(HttpStatus.CONFLICT, "User already exists, username or email is already taken");
            }
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user = modelMapper.map(userDto, User.class);
            user.setRoles(List.of(roleService.findRoleById("USER")));

            userRepository.save(user);
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método permite a un usuario iniciar sesión.
     *
     * @param identifier El nombre de usuario o correo electrónico del usuario.
     * @param password   La contraseña del usuario.
     * @return El token de acceso del usuario.
     * @throws HttpError Si las credenciales son inválidas.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token loginUser(String identifier, String password) {
        try{
            User user = userRepository.findByUsernameOrEmail(identifier, identifier);
            if(user == null || !passwordEncoder.matches(password, user.getPassword())){
                throw new HttpError(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }
            Token token = registerToken(user);
            return token;
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método busca un usuario por su nombre de usuario o correo electrónico.
     *
     * @param identifier El nombre de usuario o correo electrónico del usuario.
     * @return El usuario encontrado.
     * @throws HttpError Si el usuario no existe.
     */
    @Override
    public User findByIdentifier(String identifier) {
        try{
            User user = userRepository.findByUsernameOrEmail(identifier, identifier);
            if(user == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        }catch (HttpError e){
            throw  e;
        }
    }

    /**
     * Este método busca un usuario por su correo electrónico.
     *
     * @param email El correo electrónico del usuario.
     * @return El usuario encontrado.
     * @throws HttpError Si el usuario no existe.
     */
    @Override
    public User findByEmail(String email) {
        try{
            User user = userRepository.findByEmail(email);
            if(user == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        }catch (HttpError e){
            throw  e;
        }
    }

    /**
     * Este método busca un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario encontrado.
     * @throws HttpError Si el usuario no existe.
     */
    @Override
    public User findById(UUID id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if(user == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método busca todos los usuarios.
     *
     * @return La lista de usuarios encontrados.
     * @throws HttpError Si no se encuentran usuarios.
     */
    @Override
    public List<User> findAll() {
        try{
            List<User> users = userRepository.findAll();
            if(users.isEmpty()){
                throw new HttpError(HttpStatus.NOT_FOUND, "No users found");
            }
            return users;
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método actualiza los roles de un usuario.
     *
     * @param user El ID del usuario a actualizar.
     * @param updateRolesDto El DTO con los roles a agregar o eliminar.
     * @throws HttpError Si el usuario no existe o si se intenta eliminar el último rol.
     */
    @Override
    public void updateRoles(UUID user, UpdateRolesDto updateRolesDto) {
        try{
            User userToUpdate = findById(user);

            if(updateRolesDto.getAction().equals(RoleActions.ADD)){
                userToUpdate.getRoles().add(roleService.findRoleById(updateRolesDto.getRoleId()));
            }else if(updateRolesDto.getAction().equals(RoleActions.REMOVE) && userToUpdate.getRoles().size() > 1){
                userToUpdate.getRoles().remove(roleService.findRoleById(updateRolesDto.getRoleId()));
            }
            userRepository.save(userToUpdate);
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método registra un nuevo token para un usuario.
     *
     * @param user El usuario para el que se registra el token.
     * @return El nuevo token registrado.
     * @throws HttpError Si el token no es válido.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token registerToken(User user) {
        try{
            cleanToken(user);
            String token = jwtTools.generateToken(user);
            Token newToken = new Token(token, user);
            tokenRepository.save(newToken);

            return newToken;
        }catch (HttpError e){
            throw  e;
        }
    }

    /**
     * Este método verifica si un token es válido.
     *
     * @param user  El usuario al que pertenece el token.
     * @param token El token a verificar.
     * @return true si el token es válido, false en caso contrario.
     * @throws HttpError Si el token no es válido.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanToken(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
            tokens.stream()
                    .map(t -> t.getContent().equals(token))
                    .findAny()
                    .orElseThrow(() -> new HttpError(HttpStatus.UNAUTHORIZED, "Token not valid"));
            return true;
        } catch (HttpError e) {
            throw e;
        }
    }

    /**
     * Este método limpia los tokens inactivos de un usuario.
     *
     * @param user El usuario del que se limpian los tokens.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanToken(User user) {
        List<Token>token = tokenRepository.findByUserAndActive(user, true);
        token.forEach(t -> {
            if(!jwtTools.verifyToken(t.getContent())){
                t.setActive(false);
                tokenRepository.save(t);
            }
        });
    }

    /**
     * Este método obtiene el usuario autenticado.
     *
     * @return El usuario autenticado.
     */
    @Override
    public User findUserAuthenticated() {
        try{
            String identifier = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            User user = findByIdentifier(identifier);
            return user;
        }catch (HttpError e){
            throw e;
        }
    }
}
