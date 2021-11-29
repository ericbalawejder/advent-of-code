package aoc.year2020.day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllergenAssessment {

    public static void main(String[] args) throws IOException {
        final List<List<String>> foods = readFile("src/main/java/aoc/year2020/day21/ingredients.txt");
        final List<String> allergenFreeIngredients = findAllergenFreeIngredients(foods);
        System.out.println(allergenFreeIngredients.size());
        System.out.println(canonicalDangerousIngredientList(foods));
    }

    static String canonicalDangerousIngredientList(List<List<String>> foods) {
        return String.join(",", matchFoodByAllergen(foods).values());
    }

    static List<String> findAllergenFreeIngredients(List<List<String>> foods) {
        final SortedMap<String, List<String>> ingredientsByAllergens = ingredientsByAllergens(foods);
        final List<String> ingredients = getAllIngredients(foods);
        final Set<String> allergens = getAllAllergens(ingredientsByAllergens);

        return difference(ingredients, List.copyOf(allergens));
    }

    private static SortedMap<String, String> matchFoodByAllergen(List<List<String>> foods) {
        final SortedMap<String, List<String>> ingredientsByAllergens =
                new TreeMap<>(ingredientsByAllergens(foods));
        final SortedMap<String, String> allergenMap = new TreeMap<>();

        while (allergenMap.size() < ingredientsByAllergens.size()) {
            for (String allergen : ingredientsByAllergens.keySet()) {

                final List<String> uniqueIngredients = allergenMap.values()
                        .stream()
                        .collect(Collectors.toUnmodifiableList());

                List<String> ingredients = difference(ingredientsByAllergens.get(allergen), uniqueIngredients);

                if (ingredients.size() == 1) {
                    allergenMap.put(allergen, ingredients.get(0));
                    ingredients = Collections.emptyList();
                }
                ingredientsByAllergens.put(allergen, ingredients);
            }
        }
        return Collections.unmodifiableSortedMap(allergenMap);
    }

    private static List<String> getAllIngredients(List<List<String>> foods) {
        return foods.stream()
                .map(food -> Arrays.stream(food.get(0).split(" "))
                        .collect(Collectors.toUnmodifiableList()))
                .flatMap(List::stream)
                .collect(Collectors.toUnmodifiableList());
    }

    private static Set<String> getAllAllergens(SortedMap<String, List<String>> ingredientsByAllergens) {
        return ingredientsByAllergens.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static SortedMap<String, List<String>> ingredientsByAllergens(List<List<String>> foods) {
        final SortedMap<String, List<String>> ingredientsByAllergens = new TreeMap<>();
        for (List<String> food : foods) {
            final List<String> ingredients = Arrays.stream(food.get(0).split(" "))
                    .collect(Collectors.toUnmodifiableList());

            final List<String> allergens = Arrays.stream(food.get(1).split(" "))
                    .collect(Collectors.toUnmodifiableList());

            for (String allergen : allergens) {
                if (!ingredientsByAllergens.containsKey(allergen)) {
                    ingredientsByAllergens.put(allergen, List.copyOf(ingredients));
                } else {
                    final List<String> currentIngredients = ingredientsByAllergens.get(allergen);
                    ingredientsByAllergens.put(allergen, intersection(currentIngredients, ingredients));
                }
            }
        }
        return Collections.unmodifiableSortedMap(ingredientsByAllergens);
    }

    private static <T> List<T> difference(final List<T> listA, final List<T> listB) {
        return listA.stream()
                .filter(e -> !listB.contains(e))
                .collect(Collectors.toUnmodifiableList());
    }

    private static <T> List<T> intersection(final List<T> listA, final List<T> listB) {
        return listA.stream()
                .filter(listB::contains)
                .collect(Collectors.toUnmodifiableList());
    }

    private static List<List<String>> readFile(String path) throws IOException {
        return Files.lines(Paths.get(path))
                .map(s -> s.replaceAll("[^a-zA-Z0-9\\s]", ""))
                .map(s -> s.split(" contains "))
                .map(Arrays::asList)
                .collect(Collectors.toUnmodifiableList());
    }

    //region Unused
    private static <T> List<T> symmetricDifference(final List<T> listA, final List<T> listB) {
        return difference(union(listA, listB), intersection(listA, listB));
    }

    private static <T> List<T> union(final List<T> listA, final List<T> listB) {
        return Stream.concat(listA.stream(), listB.stream())
                .collect(Collectors.toUnmodifiableList());
    }
    //endregion

}
