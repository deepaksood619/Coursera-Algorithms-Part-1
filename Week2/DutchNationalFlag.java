// 0 - low-1 (zero)
// low - mid-1 (one)
// high+1 - n (two)
// mid - high (unknown)

class DutchNationalFlag {

    public static void sort012(int[] arr, int len) {
        int low = 0;
        int mid = 0;
        int high = len-1;

        while(mid <= high) {
            switch (arr[mid]) {
                case 0:
                    swap(arr, mid, low);
                    mid++;
                    low++;
                    break;
                
                case 1:
                    mid++;
                    break;

                case 2:
                    swap(arr, high, mid);
                    high--;
                    break;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }

    public static void main (String[] args)
    {
        int arr[] = {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};
        int arr_size = arr.length;
        sort012(arr, arr_size);
        System.out.println("Array after seggregation ");
        
        for (int i = 0; i < arr_size; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}