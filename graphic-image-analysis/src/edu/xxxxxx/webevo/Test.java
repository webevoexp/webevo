package edu.xxxxxx.webevo;

public class Test {

	private static int compare(String str, String target) {
		int d[][]; //
		int n = str.length();
		int m = target.length();
		int i; //
		int j; //
		char ch1; //
		char ch2; //
		int temp; //
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) { //
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) { //
			d[0][j] = j;
		}

		for (i = 1; i <= n; i++) { //
			ch1 = str.charAt(i - 1);
			//
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}

				//
				d[i][j] = Math.min(d[i - 1][j] + 1, Math.min(d[i][j - 1] + 1, d[i - 1][j - 1] + temp));
			}
		}
		return d[n][m];
	}

	private int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}

	public static float getSimilarityRatio(String str, String target) {
		return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
	}

	public static void main(String[] args) {

		String str = "这是一个开心幸�?的故事";
		String target = "这是一个故事";
		System.out.println("similarityRatio=" + getSimilarityRatio(str, target));
	}
}
