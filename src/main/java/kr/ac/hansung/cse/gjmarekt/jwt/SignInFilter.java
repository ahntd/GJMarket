package kr.ac.hansung.cse.gjmarekt.jwt;

import io.jsonwebtoken.security.Password;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SignInFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public SignInFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        //url을 설정한다.
        setFilterProcessesUrl("/api/signin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        // 클라이언트 요청에서 email, password 추출
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("email: " + email);
        System.out.println("password: " + password);

//        String username=obtainUsername(request);
//        String password=obtainPassword(request);
//        System.out.println("username:"+username);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authRequest);
    }

    // 로그인 성공시 실행
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

    }

    // 로그인 실패시 실행
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {

    }
}
