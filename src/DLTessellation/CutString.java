package DLTessellation;
import java.util.StringTokenizer;
public class CutString 
{
		private String str;		
		public CutString(String st)
		{
			str = st;
		}
		public String[] split()
		{
			String[] vst = new String[2];
			StringTokenizer tt = new StringTokenizer(str);
			int i = 0;
			while(tt.hasMoreTokens())
			{
				vst[i]=tt.nextToken();
				i++;
			}
			
			return vst;
		}
}

