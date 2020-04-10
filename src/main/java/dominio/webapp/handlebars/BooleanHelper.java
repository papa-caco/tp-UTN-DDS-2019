package dominio.webapp.handlebars;
import java.io.IOException;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

public enum BooleanHelper implements Helper<Boolean>{
	
	isTrue{
		@Override
		public CharSequence apply(Boolean arg0, Options arg1) throws IOException {
			if (arg0)
				return "Sí";
			else
				return "No";
		}
	}
	
}