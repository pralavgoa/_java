/*
* Generate all subsequesnces of length r
*/
class Subsequences{
	public static void main(String[] args){
		if (args==null||args.length<2){
			System.out.println("Error in args");
			return;
		}
		String s = args[0];
		int r = Integer.parseInt(args[1]);

		if(s.length()==0||r<=0||r>s.length()){
			System.out.println("Error in input");
			return;
		}
		int count = subsequences(s,r);
		System.out.println(count);
	}

	public static int subsequences(String s, int r){
		int count=0;
		for(int i=0;i<s.length();i++){
			for(int j=i+r-1;j<=s.length();j++){
				String substring = s.substring(i,j);
				if(substring.length()==r){
					System.out.println(substring);
					count++;
				}
			}
		}
		return count;
	}
}