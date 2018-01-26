class MergeSmall {

    private static void merge(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        int n = (low + high) / 2 + 1;
        
        int[] aux = new int[n];
        
        // copy first half to aux
        for (int i = 0; i < n; i++) {
            aux[i] = arr[i];
        }
        // index for first half of array
        int i = 0;

        // index for second half of array
        int j = n;

        // index for auxiliary array
        int k = 0;

        while(i <= high) {
            if (k >= n) {
                arr[i++] = arr[j++];
            } else if(j > high) {
                arr[i++] = aux[k++];
            } else if (arr[j] < aux[k]) {
                arr[i++] = arr[j++];
            } else {
                arr[i++] = aux[k++];
            }
        }

    }

    public static void main(String[] args) {
        int[] arr = {2,4,6,1,3,5};
        merge(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}