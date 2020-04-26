package com.mrh0.qspl.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import com.mrh0.qspl.type.TArray;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.TFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;

public class ArrayUtil {
	
	//Source: https://www.geeksforgeeks.org/merge-sort/
	// Merges two subarrays of arr[]. 
    // First subarray is arr[l..m] 
    // Second subarray is arr[m+1..r] 
    public static void merge(int arr[], int l, int m, int r) { 
        // Find sizes of two subarrays to be merged 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        /* Create temp arrays */
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
  
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i) 
            L[i] = arr[l + i]; 
        for (int j=0; j<n2; ++j) 
            R[j] = arr[m + 1+ j]; 
  
  
        /* Merge the temp arrays */
  
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0; 
  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < n1 && j < n2) { 
            if (L[i] <= R[j]) { 
                arr[k] = L[i]; 
                i++; 
            } 
            else{ 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
  
        /* Copy remaining elements of L[] if any */
        while (i < n1) { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        } 
  
        /* Copy remaining elements of R[] if any */
        while (j < n2) { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 
  
    // Main function that sorts arr[l..r] using 
    // merge() 
    public static void sort(int arr[], int l, int r) { 
        if (l < r) { 
            // Find the middle point 
            int m = (l+r)/2; 
  
            // Sort first and second halves 
            sort(arr, l, m); 
            sort(arr , m+1, r); 
  
            // Merge the sorted halves 
            merge(arr, l, m, r); 
        } 
    }
    
    
  //Source: https://www.geeksforgeeks.org/merge-sort/
// Merges two subarrays of arr[]. 
// First subarray is arr[l..m] 
  // Second subarray is arr[m+1..r] 
  public static void merge(ArrayList<Val> arr, int l, int m, int r) { 
      // Find sizes of two subarrays to be merged 
  int n1 = m - l + 1; 
  int n2 = r - m; 

  /* Create temp arrays */
  Val L[] = new Val[n1]; 
  Val R[] = new Val[n2]; 

  /*Copy data to temp arrays*/
  for (int i=0; i<n1; ++i) 
      L[i] = arr.get(l + i); 
  for (int j=0; j<n2; ++j) 
      R[j] = arr.get(m + 1+ j); 


  /* Merge the temp arrays */

  // Initial indexes of first and second subarrays 
  int i = 0, j = 0; 

  // Initial index of merged subarry array 
  int k = l; 
  while (i < n1 && j < n2) { 
      if (L[i].compare(R[j]) != 1 ) { //<=
          arr.set(k, L[i]); 
          i++; 
      } 
      else{ 
          arr.set(k, R[j]); 
          j++; 
      } 
      k++; 
  } 

  /* Copy remaining elements of L[] if any */
  while (i < n1) { 
      arr.set(k, L[i]); 
      i++; 
      k++; 
  } 

  /* Copy remaining elements of R[] if any */
      while (j < n2) { 
          arr.set(k, R[j]); 
          j++; 
          k++; 
      } 
  } 

  public static void sort(ArrayList<Val> arr, int l, int r) { 
      if (l < r) { 
          int m = (l+r)/2; 

          sort(arr, l, m); 
          sort(arr , m+1, r); 

          merge(arr, l, m, r); 
      } 
  }
  
  public static ArrayList<Val> iteratorSort(Iterator<Val> itr, SortingType st) {
	  ArrayList<Val> a = new ArrayList<Val>();
	  int i = 0;
	  while(itr.hasNext()) {
		  Val x = itr.next();
		  int j = st==SortingType.ASC?Math.abs(binarySearchAsc(a, 0, i++, x) + 1):Math.abs(binarySearchDesc(a, 0, i++, x) + 1);
		  a.add(j, x);
	  }
	  return a;
  }
  
  public static ArrayList<Val> iteratorSort(Iterator<Val> itr, TFunc f, VM vm) {
	  ArrayList<Val> a = new ArrayList<Val>();
	  int i = 0;
	  while(itr.hasNext()) {
		  Val x = itr.next();
		  int j = Math.abs(binarySearch(a, 0, i++, x, f, vm) + 1);
		  a.add(j, x);
	  }
	  return a;
  }
  
  //Source: https://www.geeksforgeeks.org/binary-insertion-sort/
  public void sort(ArrayList<Val> array) { 
      for (int i = 1; i < array.size(); i++) { 
          Val x = array.get(i);  
          int j = Math.abs(binarySearchAsc(array, 0, i, x) + 1); 
          array.add(j, x); 
      } 
  }
      
    //Source: java.util.Arrays
	private static int binarySearchAsc(ArrayList<Val> a, int fromIndex, int toIndex, Val key) {
		int low = fromIndex;
		int high = toIndex - 1;
		
		while (low <= high) {
			int mid = (low + high) >>> 1;
			Val midVal = a.get(mid);
			int cmp = midVal.compare(key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	
	//Source: java.util.Arrays
		private static int binarySearchDesc(ArrayList<Val> a, int fromIndex, int toIndex, Val key) {
			int low = fromIndex;
			int high = toIndex - 1;
			
			while (low <= high) {
				int mid = (low + high) >>> 1;
				Val midVal = a.get(mid);
				int cmp = key.compare(midVal);
				if (cmp < 0)
					low = mid + 1;
				else if (cmp > 0)
					high = mid - 1;
				else
					return mid;
			}
			return -(low + 1);
		}
	
	private static int binarySearch(ArrayList<Val> a, int fromIndex, int toIndex, Val key, TFunc f, VM vm) {
		int low = fromIndex;
		int high = toIndex - 1;
		
		while (low <= high) {
			int mid = (low + high) >>> 1;
			Val midVal = a.get(mid);
			int cmp = TNumber.from(f.execute(vm, TUndefined.getInstance(), new Arguments().put(midVal).put(key)).getResult()).integerValue();
			//int cmp = midVal.compare(key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
}
