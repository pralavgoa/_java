/*
* Merge sort an array
*/

import java.util.Arrays;

class MergeSort{
public static void main(String[] args){

int[] input1 = generateRandomIntArray(8);
printArray(input1);
int[] output1 = mergeSort(input1);
printArray(output1);
}

public static int[] mergeSort(int[] a){
	int[] result = new int[a.length];
	if(a.length==1){
		return a;
	}

	int mid = (int)Math.floor(a.length/2);
	result = merge(mergeSort(Arrays.copyOfRange(a,0,mid)),mergeSort(Arrays.copyOfRange(a,mid,a.length)));

	return result;
}

public static int[] merge(int[] a, int[] b){
	int totalLength = a.length + b.length;
	int[] result = new int[totalLength];

	int aIndex=0;
	int bIndex=0;
	for(int i=0;i<totalLength;i++){
		if(aIndex==a.length){
			result[i]=b[bIndex++];
			continue;
		}
		if(bIndex==b.length){
			result[i]=a[aIndex++];
			continue;
		}
		if(a[aIndex]<=b[bIndex]){
			result[i]=a[aIndex++];
		}else{
			result[i]=b[bIndex++];
		}
	}

	return result;
}

public static int[] generateRandomIntArray(int size){
	int[] result = new int[10];
	for(int i=0;i<size;i++){
		result[i] = (new Double(Math.random() * 10)).intValue();
	}
	return result;
}

public static void printArray(int[] array){
	System.out.print('[');	
	for(int e : array){
		System.out.print(e+",");
	}
	System.out.println(']');
}
}

