/*
* Print all combination of input string, r characters at a time
*/
class Permutations{
public static void main(String[] args){

	if(args==null||args.length <2){
		System.out.println("Error in args");
		return;
	}

	String s = args[0];
	int r = Integer.parseInt(args[1]);

	if(s==null||s.length()<=0||r<0||r>s.length()){
		System.out.println("Error in input");
		return;
	}
	int total = permute(s,r,"");
	System.out.println("Total count: "+total);
}

public static int permute(String s, int r, String prefix){
	if(prefix.length() == r || s.length()==0){
		System.out.println(prefix);
		return 1;
	}
	int count=0;
	for(int i=0;i<s.length();i++){
		char c = s.charAt(i);
		String remainder = s.substring(0,i)+s.substring(i+1,s.length());
		count+=permute(remainder, r,prefix+c);
	}
	return count;
}	
}
