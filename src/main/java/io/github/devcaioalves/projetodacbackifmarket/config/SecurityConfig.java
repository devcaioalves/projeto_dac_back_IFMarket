package io.github.devcaioalves.projetodacbackifmarket.config;

import io.github.devcaioalves.projetodacbackifmarket.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos de usuário
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/usermanagent/v1/criar-usuario").permitAll()
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/usermanagent/v1/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/auth").permitAll()

                        // Endpoints restritos de usuário
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/usermanagent/v1/buscar-usuario/**").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/usermanagent/v1/buscar-todos-usuarios").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/br/com/ifmarket/usermanagent/v1/atualizar-usuario/**").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.PATCH, "/br/com/ifmarket/usermanagent/v1/alterar-senha-usuario/**").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/br/com/ifmarket/usermanagent/v1/deleta-usuario/**").hasRole("ADMINISTRADOR")

                        // Endpoints públicos de itens (consulta)
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/itemmanagent/v1/buscar-item/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/itemmanagent/v1/buscar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/itemmanagent/v1/buscar-todos-itens").permitAll()
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/itemmanagent/v1/buscar-todos-itens-usuario/**").permitAll()

                        // Endpoints restritos de itens (CRUD)
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/itemmanagent/v1/criar-item").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/br/com/ifmarket/itemmanagent/v1/atualizar-item/**").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/br/com/ifmarket/itemmanagent/v1/deleta-item/**").hasRole("ADMINISTRADOR")

                        // Endpoints públicos de categorias (consulta)
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/categoriamanagent/v1/buscar-categoria/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/categoriamanagent/v1/buscar-todas-categorias").permitAll()

                        // Endpoints restritos de categorias (CRUD)
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/categoriamanagent/v1/criar-categoria").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/br/com/ifmarket/categoriamanagent/v1/atualizar-categoria/**").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/br/com/ifmarket/categoriamanagent/v1/deleta-categoria/**").hasRole("ADMINISTRADOR")

                        // Endpoints públicos de fotos
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/fotomanagent/v1/buscar-foto/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/fotomanagent/v1/criar-foto").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/br/com/ifmarket/fotomanagent/v1/deleta-foto/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

                        // Endpoints públicos de notificações (consulta)
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/notificacaomanagent/v1/buscar-notificacao/**").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/notificacaomanagent/v1/buscar-todas-notificacoes").hasRole("ADMINISTRADOR")

                        // Endpoints restritos de notificações (CRUD)
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/notificacaomanagent/v1/criar-notificacao").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/br/com/ifmarket/notificacaomanagent/v1/atualizar-notificacao/**").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/br/com/ifmarket/notificacaomanagent/v1/deleta-notificacao/**").hasRole("ADMINISTRADOR")

                        // Endpoints restritos de propostas
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/propostamanagent/v1/buscar-proposta/**").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/br/com/ifmarket/propostamanagent/v1/buscar-todas-proposta").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/propostamanagent/v1/criar-proposta").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/br/com/ifmarket/propostamanagent/v1/atualizar-proposta/**").hasAnyRole("ALUNO", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/br/com/ifmarket/propostamanagent/v1/deleta-proposta/**").hasRole("ADMINISTRADOR")

                        // Endpoints públicos de recuperação de senha
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/recuperarsenha/v1/esqueci-senha").permitAll()
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/recuperarsenha/v1/redefinir-senha").permitAll()
                        .requestMatchers(HttpMethod.POST, "/br/com/ifmarket/recuperarsenha/v1/validar-token").permitAll()

                        // Swagger e docs
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
