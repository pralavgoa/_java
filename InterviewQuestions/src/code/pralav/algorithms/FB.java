/*
* push non-zero elements to the left to replace zeroes in an array, and return the count of non-zero elements
* [0 0 1 3 5] => [3 1 5 0 0]
*/
import java.util.Arrays;

class FB{
	public static void main(String[] args){
		debug(move(new int[]{0,0,1,3,5}));
		debug(move(new int[]{0,0,0,0,0}));
		debug(move(new int[]{1,3,0,5,0}));
		debug(move(new int[]{0,1,3,5,0}));
		debug(move(new int[]{}));
	}

	private static int move(int[] a){
		debug(Arrays.toString(a));

		int count = 0;
		int ni = a.length-1;

		while(count<ni){
			while(count<ni && a[count]!=0){
			count++;
			}

			while(ni>count && a[ni]==0){
			ni--;
			}
			a[count]=a[ni];
			a[ni]=0;

		}
		debug(Arrays.toString(a));
		return count;
	}

	private static void debug(Object s){
		System.out.println(s);
	}
}