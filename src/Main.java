import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arr = new int[n];
        for(int i = 0;i<n;i++){
            arr[i] = scan.nextInt();
        }
        int[] minn = new int[3];
        minn[0] = arr[0];
        minn[1] = arr[1];
        minn[2] = arr[2];
        adjust(minn);
        System.out.print(minn[2]+" ");
        for(int i = 3;i<n;i++){
            if(arr[i]<minn[2]){
                minn[2] = arr[i];
                adjust(minn);
            }
            System.out.print(minn[2]+" ");
        }
        scan.close();
    }
    public static void swap(int[] arr, int i, int j) {
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }
    public static void adjust(int[] minn){
        if(minn[0] > minn[1]){
            swap(minn,0,1);
        }
        if(minn[0] > minn[2]){
            swap(minn,0,2);
        }
        if(minn[1]>minn[2]){
            swap(minn,1,2);
        }
    }
}
