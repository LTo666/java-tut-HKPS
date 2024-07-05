
class Lesson05ASM1 {

    public static void main(String[] args) {
        int arr1[] = { 5, 3, 8, 4, 6, 20, 11, 3, 5 };
        for (int z = 0; z < arr1.length; z++) {
            for (int x = 0; x < arr1.length - z - 1; x++) {
                if (arr1[x] > arr1[x + 1]) {
                    int temp = arr1[x];
                    arr1[x] = arr1[x + 1];
                    arr1[x + 1] = temp;
                }
            }
        }

        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + " ");
        }
        System.out.println();
    }
}
