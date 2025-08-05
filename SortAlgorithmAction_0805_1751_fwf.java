// 代码生成时间: 2025-08-05 17:51:29
import com.opensymphony.xwork2.ActionSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SortAlgorithmAction is a Struts2 action class that provides functionality for sorting algorithms.
 * It allows users to choose between different sorting methods and sorts a list of integers.
 * The result is then returned to the user.
 */
public class SortAlgorithmAction extends ActionSupport {

    private List<Integer> list; // List to hold the integers to be sorted
    private String sortAlgorithm; // Algorithm to use for sorting
    private List<Integer> sortedList; // Sorted list result

    /**
     * Constructor
     */
    public SortAlgorithmAction() {
        list = Arrays.asList(5, 3, 8, 4, 2); // Example list of integers to be sorted
    }

    /**
     * Sets the list of integers to be sorted.
     *
     * @param list List of integers
     */
    public void setList(List<Integer> list) {
        this.list = list;
    }

    /**
     * Gets the list of integers to be sorted.
     *
     * @return List of integers
     */
    public List<Integer> getList() {
        return list;
    }

    /**
     * Sets the sort algorithm to be used.
     *
     * @param sortAlgorithm String representing the sorting algorithm
     */
    public void setSortAlgorithm(String sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }

    /**
     * Gets the sort algorithm to be used.
     *
     * @return String representing the sorting algorithm
     */
    public String getSortAlgorithm() {
        return sortAlgorithm;
    }

    /**
     * Sets the sorted list result.
     *
     * @param sortedList List of sorted integers
     */
    public void setSortedList(List<Integer> sortedList) {
        this.sortedList = sortedList;
    }

    /**
     * Gets the sorted list result.
     *
     * @return List of sorted integers
     */
    public List<Integer> getSortedList() {
        return sortedList;
    }

    public String execute() {
        try {
            if (list == null || list.isEmpty() || sortAlgorithm == null || sortAlgorithm.isEmpty()) {
                addActionError("List or algorithm cannot be empty.");
                return ERROR;
            }

            // Choose sorting method based on the provided algorithm name
            switch (sortAlgorithm) {
                case "bubbleSort":
                    sortedList = bubbleSort(list);
                    break;
                case "quickSort":
                    sortedList = quickSort(list);
                    break;
                default:
                    addActionError("Sorting algorithm not supported.");
                    return ERROR;
            }

            // Add success message after sorting
            addActionMessage("List sorted successfully using " + sortAlgorithm + " algorithm.");

        } catch (Exception e) {
            addActionError("Error occurred during sorting: " + e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * Sorts the list using bubble sort algorithm.
     *
     * @param list List to be sorted
     * @return Sorted list
     */
    private List<Integer> bubbleSort(List<Integer> list) {
        boolean swapped;
        int size = list.size();
        do {
            swapped = false;
            for (int i = 1; i < size; i++) {
                if (list.get(i - 1) > list.get(i)) {
                    // Swap elements
                    int temp = list.get(i - 1);
                    list.set(i - 1, list.get(i));
                    list.set(i, temp);
                    swapped = true;
                }
            }
            size--;
        } while (swapped);

        return list;
    }

    /**
     * Sorts the list using quick sort algorithm.
     *
     * @param list List to be sorted
     * @return Sorted list
     */
    private List<Integer> quickSort(List<Integer> list) {
        return quickSort(list, 0, list.size() - 1);
    }

    private List<Integer> quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1); // Before pi
            quickSort(list, pi + 1, high); // After pi
        }
        return list;
    }

    private int partition(List<Integer> list, int low, int high) {
        int pivot = list.get(high);
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            if (list.get(j) < pivot) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
