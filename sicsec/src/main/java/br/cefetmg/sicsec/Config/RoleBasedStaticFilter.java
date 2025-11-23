package br.cefetmg.sicsec.Config;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import br.cefetmg.sicsec.dto.Perfil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RoleBasedStaticFilter extends OncePerRequestFilter {

    // Mapeamento de cargo para pasta permitida
    private static final Map<Cargo, String[]> ROLE_PATHS = new HashMap<>();

    static {
        ROLE_PATHS.put(Cargo.ALUNO, new String[] {
                "/html/aluno/",
                "/css/aluno/",
                "/js/aluno/",
        });

        ROLE_PATHS.put(Cargo.PROFESSOR, new String[] {
                "/html/professor/",
                "/css/professor/",
                "/js/professor/"
        });

        ROLE_PATHS.put(Cargo.ADMINISTRADOR, new String[] {
                "/html/admin/",
                "/css/admin/",
                "/js/admin/"
        });
    }

    private static final String[] STATIC_PATHS = {
            "/css/",
            "/js/",
            "/html/",
            "/images/"
    };

    private static final String[] PUBLIC_RESOURCES = {
            // CSS público
            "/css/base.css",
            "/css/login/",
            "/css/home/",
            
            // JS público  
            "/js/login/",
            "/js/home/",
            
            // Images
            "/images/",
            
            // Páginas públicas
            "/login", 
            "/home",
            "/logout",
            
            // Favicon
            "/favicon.ico"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Verifica se não é um arquivo estático
        if (!isStaticResource(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Verifica se é um recurso público
        if (isPublicResource(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Se não é público, aplica autenticação
        Perfil perfil = (Perfil) request.getSession().getAttribute("perfilSelecionado");
        
        // Se não está logado, redireciona para login
        if (perfil == null || !perfil.getLogado()) {
            response.sendRedirect("/login");
            return;
        }

        Usuario usuario = perfil.getUsuario();

        // Verifica permissão baseada no cargo
        if (!isAccessAllowed(path, usuario.getCargo())) {
            request.getSession().setAttribute("error", 
                "Acesso negado. Seu cargo (" + usuario.getCargo() + ") não tem permissão para: " + path);
            response.sendRedirect("/home");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isResourcePresent(String path, String[] allowedPaths) {
        for (String allowedPath : allowedPaths) {
            if (path.equals(allowedPath) || path.startsWith(allowedPath)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAccessAllowed(String path, Cargo cargo) {
        String[] allowedPaths = ROLE_PATHS.get(cargo);
        if (allowedPaths == null) return false;

        return isResourcePresent(path, allowedPaths);
    }

    private boolean isStaticResource(String path) {
        return isResourcePresent(path, STATIC_PATHS);
    }

    private boolean isPublicResource(String path) {
        return isResourcePresent(path, PUBLIC_RESOURCES);
    }
}