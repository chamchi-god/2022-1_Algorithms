public class DeterministicSelect {
    public int findKthLargest(int[] nums, int k) {
        return quicksort(nums, 0, nums.length - 1, k);
    }

    private int quicksort(int[] nums, int start, int end, int k) {
        int N = end - start + 1;
        int index = start + (int) (Math.random() * N);
        int pivot = nums[index];
        swap(nums, index, end);
        int i = start;
        for (int j = start; j < end; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, end, i);
        int count = end - i + 1;
        if (k == count) {
            return pivot;
        } else if (k < count) {
            return quicksort(nums, i + 1, end, k);
        } else {
            return quicksort(nums, start, i - 1, k - count);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }
    public void deterministicSelect(int[] array, int p, int r){

    }
}
