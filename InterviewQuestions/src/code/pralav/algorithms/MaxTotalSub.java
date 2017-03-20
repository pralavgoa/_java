/*
* Given an array, find the maximal total subsequence
*/
import java.util.Arrays;

class MaxTotalSub{
	public static void main(String[] args){
		int[] input1 = {23,57,-93,57,1,49,-34, 2,23,44};
		debug(input1);
		int[] result = maxTotalSub(input1);
		debug(result);
		result = maxTotalSubN(input1);
		debug(result);
	}

	//n squared algorithm
	public static int[] maxTotalSub(int[] a){
		int[] result = new int[a.length];

		int maxTotal = Integer.MIN_VALUE;
		
		for(int i=0;i<a.length;i++){
			int currentTotal = 0;
			for(int j=i;j<a.length;j++){
				currentTotal += a[j];
				if(currentTotal>maxTotal){
					maxTotal = currentTotal;
					result =  Arrays.copyOfRange(a,i,j+1);
				}
			}

		}

		return result;
	}

	public static int[] maxTotalSubN(int[] a){
		int[] result = new int[a.length];
		int maxTotal = Integer.MIN_VALUE;

		int startIndex = 0;
		int endIndex = 0;
		int currentTotal = 0;
		for(int i=0;i<a.length;i++){
			currentTotal += a[i];
			if(currentTotal<0){
				currentTotal=0;
				startIndex=i+1;
			}

			if(currentTotal > maxTotal){
				maxTotal = currentTotal;
				endIndex = i;
			}
		}

		return Arrays.copyOfRange(a,startIndex,endIndex+1);
	}

	public static void debug(int[] a){
		System.out.print('[');
		for(int n : a){
			System.out.print(n+",");
		}
		System.out.print(']');
	}

}