class Combinations{
	public static void main(String[] args){
		if(args==null||args.length<2){
			System.out.println("Error in arguments");
			return;
		}

		String s = args[0];
		int r = Integer.parseInt(args[1]);

		if(s==null||s.length()<0||r<0|r>s.length()){
			System.out.println("Error in input");
			return;
		}

		int count = combinations(s,r,"");
		System.out.println("Total:"+count);
	}

	public static int combinations(String s, int r, String prefix){
		if(prefix.length()==r){
			System.out.println(prefix);
			return 1;
		}
		int count=0;
		for(int i=0;i<s.length();i++){
			char c = s.charAt(i);
			String remainder = s.substring(i+1,s.length());
			count+=combinations(remainder,r,prefix+c);
		}
		return count;
	}
}