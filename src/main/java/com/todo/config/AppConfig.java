//package com.todo.config;
//
//import com.todo.exceptions.NotFoundException;
//import com.todo.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//@RequiredArgsConstructor
//public class AppConfig {
//
//    private final UserRepository userRepository;
////    @Bean
////    public UserDetailsService userDetailsService() {
////        return username -> userRepository.findByUsername(username)
////                .orElseThrow(() -> new NotFoundException("User not found"));
////    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
//        return conf.getAuthenticationManager();
//    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return null;
//                //from repo find by name
//            }
//        }
//
////        UserDetails user1 = User.withUsername("user1")
////                .password(passwordEncoder().encode("user1Pass"))
////                .roles("USER")
////                .build();
////        UserDetails user2 = User.withUsername("user2")
////                .password(passwordEncoder().encode("user2Pass"))
////                .roles("USER")
////                .build();
////        UserDetails admin = User.withUsername("admin/**")
////                .password(passwordEncoder().encode("adminPass"))
////                .roles("ADMIN")
////                .build();
////        return new InMemoryUserDetailsManager(user1, user2, admin);
////    }

//   @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findByUsername(username)
//                .orElseThrow(() -> new NotFoundException("User not found"));
//    }
//}
