import { useEffect, useState } from "react";
import { itemService } from "../services/itemService.js";

export function useItems({ page, pageSize, debouncedSearch, debouncedBrand, debouncedCategory, filters }) {
  const [items, setItems] = useState([]);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(true);

  const debouncedKeys = ['search', 'brand', 'category'];
  const immediateFilterKeys = Object.keys(filters).filter(key => !debouncedKeys.includes(key));
  const immediateFilterValues = immediateFilterKeys.map(key => filters[key]);

  useEffect(() => {
    let isMounted = true;

    async function loadItems() {
      try {
        setLoading(true);

        const hasFilters = debouncedSearch || debouncedBrand || debouncedCategory || immediateFilterValues.some(value => value !== "" && value !== null && value !== undefined);
        let response;

        if (hasFilters) {
          const filtersObj = {
            query: debouncedSearch,
            brand: debouncedBrand || null,
            category: debouncedCategory || null,
          };

          Object.entries(filters).forEach(([key, value]) => {
            if (value === "" || value === null || value === undefined || key === "search" || key === "brand" || key === "category") {
              return;
            }
            filtersObj[key] = value;
          });

          response = await itemService.searchItems("/items/search/filter", filtersObj, page, pageSize);
        } else {
          response = await itemService.getItems(page, pageSize);
        }

        if (!isMounted) return;
        setItems(Array.isArray(response?.content) ? response.content : []);
        setTotalPages(response?.totalPages ?? 1);
      } catch (error) {
        console.error("No se pudo cargar los items", error);
        setItems([]);
        setTotalPages(1);
      } finally {
        if (isMounted) setLoading(false);
      }
    }

    loadItems();
    return () => { isMounted = false; };
  }, [page, debouncedSearch, debouncedBrand, debouncedCategory, ...immediateFilterValues, pageSize]);

  return { items, totalPages, loading, setItems, setTotalPages };
}
