import java.util.*;
public class PascalsTriangle {
    public static void main(String[] args){
        System.out.println(getRow(3));
    }
    public static List<Integer> getRow(int k) {
        List<Integer> result = new ArrayList<Integer>();
        if(k<0){
            return result;
        }
        /*
        * i[0...k] j[0...i]
        * k=1, 0(0) 1(0,1)
        */
        List<Integer> prev = new ArrayList<Integer>();
        for(int i=0;i<=k;i++){
            for(int j=1;j<i;j++){
                    result.set(j,prev.get(j-1)+prev.get(j));
            }
            result.add(1);
            prev=new ArrayList<>(result);
        }
        return result;
    }
}