/*
* Take an input number and print a tree of that height
*/

class PrintTree{
	public static void main(String[] args){
		if(args==null||args.length==0){
			System.out.println("Error in args");
			return;
		}
		int n = Integer.parseInt(args[0]);
		if(n<=0){
			System.out.println("Error in input");
			return;
		}
		printTree(n);
	}
	private static void printTree(int n){
		int width = 2*(n-1)+1;
		for(int hInd = 0;hInd<n;hInd++){
			int rowStars = 2*hInd+1;
			int emptySpaces = width - rowStars;
			for(int wInd = 0;wInd<width;wInd++){
				if(wInd<emptySpaces/2||wInd>(width-emptySpaces/2)-1){
					System.out.print(" ");
				}else{
					System.out.print("*");
				}
			}
			System.out.println("");
		}
		
	}
}