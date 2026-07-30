package org.ranking_app.service.item;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.dto.request.item.ItemSearchFilterRequest;
import org.ranking_app.dto.request.item.ItemSearchRequest;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.service.user.UserFinderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ranking_app.service.jwt.JwtService;

@Service
public class ItemsSearcherService {
    private final JpaItemRepository jpaItemRepository;
    private final UserFinderService userFinderService;
    private final JwtService jwtService;

    public ItemsSearcherService(
            JpaItemRepository jpaItemRepository,
            UserFinderService userFinderService,
            JwtService jwtService) {
        this.jpaItemRepository = jpaItemRepository;
        this.userFinderService = userFinderService;
        this.jwtService = jwtService;
    }

    public Page<Item> findAll(Pageable pageable) {
        return jpaItemRepository.findAll(pageable);
    }

    public Page<Item> search(ItemSearchRequest request, Pageable pageable, HttpServletRequest httpRequest) {
        String pattern = null;
        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            pattern = "%" + request.getQuery() + "%";
        }
        if (request instanceof ItemSearchFilterRequest filter) {
            Boolean suggested = filter.getSuggested();
            Boolean enabled = filter.getEnabled();

            if (httpRequest != null) {
                if (jwtService.hasValidToken(httpRequest)) {
                    User authenticatedUser = userFinderService.findAuthenticatedUser(httpRequest);

                    if (!authenticatedUser.getAdmin()) {
                        suggested = false;
                        enabled = true;
                    }
                } else {
                    suggested = false;
                    enabled = true;
                }
            }

            String brand = null;
            if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
                brand = "%" + filter.getBrand() + "%";
            }
            String category = null;
            if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
                category = "%" + filter.getCategory() + "%";
            }
            return jpaItemRepository.findByFilters(
                    pattern,
                    brand,
                    suggested,
                    enabled,
                    category,
                    pageable);
        }
        return jpaItemRepository.findByFilters(
                pattern,
                null,
                null, // navbar search no muestra items sugeridos
                true, // navbar search solo muestra items habilitados
                null,
                pageable);
    }
}
