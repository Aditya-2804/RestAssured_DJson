package TestCases;

import Base.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Demo {

    @Test
    public void firstDemo(){

        int[] arr = {1,2,3,4,5,6,7};
        int n =arr.length;
        int k=2;

        revArray(arr,0,n-1);

        revArray(arr,0,k-1);

        revArray(arr,k,n-1);

        System.out.println(Arrays.toString(arr));
    }

    public static void revArray(int[] arr, int start, int end){
        while (start<end){
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;

        }
    }


}
