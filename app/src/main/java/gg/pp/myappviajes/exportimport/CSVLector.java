
package gg.pp.myappviajes.exportimport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVLector {
    private BufferedReader br;
    private boolean hasNext = true;
    private char separator;
    private char quotechar;
    private int skipLines;
    private boolean linesSkiped;
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    public static final int DEFAULT_SKIP_LINES = 0;
    
    public CSVLector(Reader reader) {
        this(reader, DEFAULT_SEPARATOR);
    }
    
    public CSVLector(Reader reader, char separator) {
        this(reader, separator, DEFAULT_QUOTE_CHARACTER);
    }
    
    public CSVLector(Reader reader, char separator, char quotechar) {
        this(reader, separator, quotechar, DEFAULT_SKIP_LINES);
    }
 
    public CSVLector(Reader reader, char separator, char quotechar, int line) {
        this.br = new BufferedReader(reader);
        this.separator = separator;
        this.quotechar = quotechar;
        this.skipLines = line;
    }

    public List readAll() throws IOException {
        List allElements = new ArrayList();
        while (hasNext) {
            String[] nextLineAsTokens = readNext();
            if (nextLineAsTokens != null)
                allElements.add(nextLineAsTokens);
        }
        return allElements;
    }

    public String[] readNext() throws IOException {
        String nextLine = getNextLine();
        return hasNext ? parseLine(nextLine) : null;
    }
 
    private String getNextLine() throws IOException {
    	if (!this.linesSkiped) {
            for (int i = 0; i < skipLines; i++) {
                br.readLine();
            }
            this.linesSkiped = true;
        }
        String nextLine = br.readLine();
        if (nextLine == null) {
            hasNext = false;
        }
        return hasNext ? nextLine : null;
    }

    private String[] parseLine(String nextLine) throws IOException {
        if (nextLine == null) {
            return null;
        }
        List tokensOnThisLine = new ArrayList();
        StringBuffer sb = new StringBuffer();
        boolean inQuotes = false;
        do {
        	if (inQuotes) {
                sb.append("\n");
                nextLine = getNextLine();
                if (nextLine == null)
                    break;
            }
            for (int i = 0; i < nextLine.length(); i++) {
                char c = nextLine.charAt(i);
                if (c == quotechar) {
                	if( inQuotes  
                	    && nextLine.length() > (i+1)                  	    
                	    && nextLine.charAt(i+1) == quotechar ){ 
                		sb.append(nextLine.charAt(i+1));
                		i++;
                	}else{
                		inQuotes = !inQuotes;
                		if(i>2 
                				&& nextLine.charAt(i-1) != this.separator
                				&& nextLine.length()>(i+1) &&
                				nextLine.charAt(i+1) != this.separator 
                		){
                			sb.append(c);
                		}
                	}
                } else if (c == separator && !inQuotes) {
                    tokensOnThisLine.add(sb.toString());
                    sb = new StringBuffer(); 
                } else {
                    sb.append(c);
                }
            }
        } while (inQuotes);
        tokensOnThisLine.add(sb.toString());
        return (String[]) tokensOnThisLine.toArray(new String[0]);
    }
    public void close() throws IOException{
    	br.close();
    }
 }
