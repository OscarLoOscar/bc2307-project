import java.util.ArrayList;
import java.util.List;

public class checkAns {

  public static void main(String[] args) {
    List<String> ans = new ArrayList<>();
    ans.add("CBDBAAABA");
    ans.add("CBBDCCADA");//
    ans.add("CDDAACADA");
    ans.add("CDDDACADD");
    ans.add("CDDAACABB");
    ans.add("CBDAACABB");
    ans.add("CDDAACADB");
    ans.add("DCDEBBCBA");
    ans.add("CDDAACADA");
    ans.add("CACECADBC");
    ans.add("DDDAACABD");
    ans.add("CCDBACADA");

    String modelAns = "CDDAACABB";
    int totalMatchCount = 0;

    for (String str : ans) {
      int matchCount = 0;

      for (int i = 0; i < modelAns.length(); i++) {
        if (str.charAt(i) == modelAns.charAt(i)) {
          matchCount++;
        }
      }

      double matchRate = (double) matchCount / modelAns.length() * 100;
      System.out.println("Answer: " + str);
      System.out.println("Match Rate: " + matchRate + "%");

      totalMatchCount += matchCount;
    }

    double totalMatchRate = (double) totalMatchCount / (modelAns.length() * ans.size()) * 100;
    System.out.println("Total Match Rate: " + totalMatchRate + "%");
  }
}
