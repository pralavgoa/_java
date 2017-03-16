/*
* Print all combination of input string, r characters at a time
*/
public static void main(String[] args){

	if(args==null||args.length <=2){
		return;
	}

	String s = args[0];
	String r = args[1];

	if(s==null||s.length<=0||r<=0||r>s.length){
		System.out.println("Error in input");
		return;
	}
	permute(s,r,"");

}

public static void permute(String s, int r, String prefix){
	if(prefix.length == r || s.length==0){
		System.out.println(prefix);
		return;
	}
	for(int i=0;i<=s.length;i++){
		char c = s.charAt(i);
		String remainder = s.substring(0,i)+s.substring(i+1,s.length);
		permute(remainder, r,prefix+c);
	}
}