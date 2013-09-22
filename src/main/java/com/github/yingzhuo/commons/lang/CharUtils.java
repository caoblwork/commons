/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.commons.lang;

/**
 * <p>Operations on char primitives and Character objects.</p>
 *
 * <p>This class tries to handle {@code null} input gracefully.
 * An exception will not be thrown for a {@code null} input.
 * Each method documents its behaviour in more detail.</p>
 * 
 * <p>#ThreadSafe#</p>
 * @since 2.1
 * @version $Id: CharUtils.java 1158279 2011-08-16 14:06:45Z ggregory $
 */
public class CharUtils {
    
    private static final String[] CHAR_STRING_ARRAY = new String[128];
    
	/* The ASCII control characters, per RFC 20. */
	/**
	 * Null ('\0'): The all-zeros character which may serve to accomplish time
	 * fill and media fill. Normally used as a C string terminator.
	 * <p>
	 * Although RFC 20 names this as "Null", note that it is distinct from the
	 * C/C++ "NULL" pointer.
	 * 
	 */
	public static final byte NUL = 0;

	/**
	 * Start of Heading: A communication control character used at the beginning
	 * of a sequence of characters which constitute a machine-sensible address
	 * or routing information. Such a sequence is referred to as the "heading."
	 * An STX character has the effect of terminating a heading.
	 * 
	 */
	public static final byte SOH = 1;

	/**
	 * Start of Text: A communication control character which precedes a
	 * sequence of characters that is to be treated as an entity and entirely
	 * transmitted through to the ultimate destination. Such a sequence is
	 * referred to as "text." STX may be used to terminate a sequence of
	 * characters started by SOH.
	 * 
	 */
	public static final byte STX = 2;

	/**
	 * End of Text: A communication control character used to terminate a
	 * sequence of characters started with STX and transmitted as an entity.
	 * 
	 */
	public static final byte ETX = 3;

	/**
	 * End of Transmission: A communication control character used to indicate
	 * the conclusion of a transmission, which may have contained one or more
	 * texts and any associated headings.
	 * 
	 */
	public static final byte EOT = 4;

	/**
	 * Enquiry: A communication control character used in data communication
	 * systems as a request for a response from a remote station. It may be used
	 * as a "Who Are You" (WRU) to obtain identification, or may be used to
	 * obtain station status, or both.
	 * 
	 */
	public static final byte ENQ = 5;

	/**
	 * Acknowledge: A communication control character transmitted by a receiver
	 * as an affirmative response to a sender.
	 * 
	 */
	public static final byte ACK = 6;

	/**
	 * Bell ('\a'): A character for use when there is a need to call for human
	 * attention. It may control alarm or attention devices.
	 * 
	 */
	public static final byte BEL = 7;

	/**
	 * Backspace ('\b'): A format effector which controls the movement of the
	 * printing position one printing space backward on the same printing line.
	 * (Applicable also to display devices.)
	 * 
	 */
	public static final byte BS = 8;

	/**
	 * Horizontal Tabulation ('\t'): A format effector which controls the
	 * movement of the printing position to the next in a series of
	 * predetermined positions along the printing line. (Applicable also to
	 * display devices and the skip function on punched cards.)
	 */
	public static final byte HT = 9;

	/**
	 * Line Feed ('\n'): A format effector which controls the movement of the
	 * printing position to the next printing line. (Applicable also to display
	 * devices.) Where appropriate, this character may have the meaning
	 * "New Line" (NL), a format effector which controls the movement of the
	 * printing point to the first printing position on the next printing line.
	 * Use of this convention requires agreement between sender and recipient of
	 * data.
	 * 
	 */
	public static final byte LF = 10;

	/**
	 * Alternate name for {@link #LF}. ({@code LF} is preferred.)
	 */
	public static final byte NL = 10;

	/**
	 * Vertical Tabulation ('\v'): A format effector which controls the movement
	 * of the printing position to the next in a series of predetermined
	 * printing lines. (Applicable also to display devices.)
	 * 
	 */
	public static final byte VT = 11;

	/**
	 * Form Feed ('\f'): A format effector which controls the movement of the
	 * printing position to the first pre-determined printing line on the next
	 * form or page. (Applicable also to display devices.)
	 * 
	 */
	public static final byte FF = 12;

	/**
	 * Carriage Return ('\r'): A format effector which controls the movement of
	 * the printing position to the first printing position on the same printing
	 * line. (Applicable also to display devices.)
	 * 
	 */
	public static final byte CR = 13;

	/**
	 * Shift Out: A control character indicating that the code combinations
	 * which follow shall be interpreted as outside of the character set of the
	 * standard code table until a Shift In character is reached.
	 * 
	 */
	public static final byte SO = 14;

	/**
	 * Shift In: A control character indicating that the code combinations which
	 * follow shall be interpreted according to the standard code table.
	 * 
	 */
	public static final byte SI = 15;

	/**
	 * Data Link Escape: A communication control character which will change the
	 * meaning of a limited number of contiguously following characters. It is
	 * used exclusively to provide supplementary controls in data communication
	 * networks.
	 * 
	 */
	public static final byte DLE = 16;

	/**
	 * Device Control 1. Characters for the control of ancillary devices
	 * associated with data processing or telecommunication systems, more
	 * especially switching devices "on" or "off." (If a single "stop" control
	 * is required to interrupt or turn off ancillary devices, DC4 is the
	 * preferred assignment.)
	 * 
	 */
	public static final byte DC1 = 17; // aka XON

	/**
	 * Transmission On: Although originally defined as DC1, this ASCII control
	 * character is now better known as the XON code used for software flow
	 * control in serial communications. The main use is restarting the
	 * transmission after the communication has been stopped by the XOFF control
	 * code.
	 * 
	 */
	public static final byte XON = 17; // aka DC1

	/**
	 * Device Control 2. Characters for the control of ancillary devices
	 * associated with data processing or telecommunication systems, more
	 * especially switching devices "on" or "off." (If a single "stop" control
	 * is required to interrupt or turn off ancillary devices, DC4 is the
	 * preferred assignment.)
	 * 
	 */
	public static final byte DC2 = 18;

	/**
	 * Device Control 3. Characters for the control of ancillary devices
	 * associated with data processing or telecommunication systems, more
	 * especially switching devices "on" or "off." (If a single "stop" control
	 * is required to interrupt or turn off ancillary devices, DC4 is the
	 * preferred assignment.)
	 * 
	 */
	public static final byte DC3 = 19; // aka XOFF

	/**
	 * Transmission off. See {@link #XON} for explanation.
	 * 
	 */
	public static final byte XOFF = 19; // aka DC3

	/**
	 * Device Control 4. Characters for the control of ancillary devices
	 * associated with data processing or telecommunication systems, more
	 * especially switching devices "on" or "off." (If a single "stop" control
	 * is required to interrupt or turn off ancillary devices, DC4 is the
	 * preferred assignment.)
	 * 
	 */
	public static final byte DC4 = 20;

	/**
	 * Negative Acknowledge: A communication control character transmitted by a
	 * receiver as a negative response to the sender.
	 * 
	 */
	public static final byte NAK = 21;

	/**
	 * Synchronous Idle: A communication control character used by a synchronous
	 * transmission system in the absence of any other character to provide a
	 * signal from which synchronism may be achieved or retained.
	 * 
	 */
	public static final byte SYN = 22;

	/**
	 * End of Transmission Block: A communication control character used to
	 * indicate the end of a block of data for communication purposes. ETB is
	 * used for blocking data where the block structure is not necessarily
	 * related to the processing format.
	 * 
	 */
	public static final byte ETB = 23;

	/**
	 * Cancel: A control character used to indicate that the data with which it
	 * is sent is in error or is to be disregarded.
	 * 
	 */
	public static final byte CAN = 24;

	/**
	 * End of Medium: A control character associated with the sent data which
	 * may be used to identify the physical end of the medium, or the end of the
	 * used, or wanted, portion of information recorded on a medium. (The
	 * position of this character does not necessarily correspond to the
	 * physical end of the medium.)
	 * 
	 */
	public static final byte EM = 25;

	/**
	 * Substitute: A character that may be substituted for a character which is
	 * determined to be invalid or in error.
	 * 
	 */
	public static final byte SUB = 26;

	/**
	 * Escape: A control character intended to provide code extension
	 * (supplementary characters) in general information interchange. The Escape
	 * character itself is a prefix affecting the interpretation of a limited
	 * number of contiguously following characters.
	 * 
	 */
	public static final byte ESC = 27;

	/**
	 * File Separator: These four information separators may be used within data
	 * in optional fashion, except that their hierarchical relationship shall
	 * be: FS is the most inclusive, then GS, then RS, and US is least
	 * inclusive. (The content and length of a File, Group, Record, or Unit are
	 * not specified.)
	 * 
	 */
	public static final byte FS = 28;

	/**
	 * Group Separator: These four information separators may be used within
	 * data in optional fashion, except that their hierarchical relationship
	 * shall be: FS is the most inclusive, then GS, then RS, and US is least
	 * inclusive. (The content and length of a File, Group, Record, or Unit are
	 * not specified.)
	 * 
	 */
	public static final byte GS = 29;

	/**
	 * Record Separator: These four information separators may be used within
	 * data in optional fashion, except that their hierarchical relationship
	 * shall be: FS is the most inclusive, then GS, then RS, and US is least
	 * inclusive. (The content and length of a File, Group, Record, or Unit are
	 * not specified.)
	 * 
	 */
	public static final byte RS = 30;

	/**
	 * Unit Separator: These four information separators may be used within data
	 * in optional fashion, except that their hierarchical relationship shall
	 * be: FS is the most inclusive, then GS, then RS, and US is least
	 * inclusive. (The content and length of a File, Group, Record, or Unit are
	 * not specified.)
	 */
	public static final byte US = 31;

	/**
	 * Space: A normally non-printing graphic character used to separate words.
	 * It is also a format effector which controls the movement of the printing
	 * position, one printing position forward. (Applicable also to display
	 * devices.)
	 * 
	 */
	public static final byte SP = 32;

	/**
	 * Alternate name for {@link #SP}.
	 */
	public static final byte SPACE = 32;

	/**
	 * Delete: This character is used primarily to "erase" or "obliterate"
	 * erroneous or unwanted characters in perforated tape.
	 */
	public static final byte DEL = 127;

	/**
	 * The minimum value of an ASCII character.
	 */
	public static final char MIN = 0;

	/**
	 * The maximum value of an ASCII character.
	 */
	public static final char MAX = 127;
    

    static {
        for (char c = 0; c < CHAR_STRING_ARRAY.length; c++) {
            CHAR_STRING_ARRAY[c] = String.valueOf(c);
        }
    }

    /**
     * <p>{@code CharUtils} instances should NOT be constructed in standard programming.
     * Instead, the class should be used as {@code CharUtils.toString('c');}.</p>
     *
     * <p>This constructor is public to permit tools that require a JavaBean instance
     * to operate.</p>
     */
    public CharUtils() {
      super();
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Converts the character to a Character.</p>
     * 
     * <p>For ASCII 7 bit characters, this uses a cache that will return the
     * same Character object each time.</p>
     *
     * <pre>
     *   CharUtils.toCharacterObject(' ')  = ' '
     *   CharUtils.toCharacterObject('A')  = 'A'
     * </pre>
     *
     * @deprecated Java 5 introduced {@link Character#valueOf(char)} which caches chars 0 through 127.
     * @param ch  the character to convert
     * @return a Character of the specified character
     */
    @Deprecated
    public static Character toCharacterObject(char ch) {
        return Character.valueOf(ch);
    }
    
    /**
     * <p>Converts the String to a Character using the first character, returning
     * null for empty Strings.</p>
     * 
     * <p>For ASCII 7 bit characters, this uses a cache that will return the
     * same Character object each time.</p>
     * 
     * <pre>
     *   CharUtils.toCharacterObject(null) = null
     *   CharUtils.toCharacterObject("")   = null
     *   CharUtils.toCharacterObject("A")  = 'A'
     *   CharUtils.toCharacterObject("BA") = 'B'
     * </pre>
     *
     * @param str  the character to convert
     * @return the Character value of the first letter of the String
     */
    public static Character toCharacterObject(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return Character.valueOf(str.charAt(0));
    }
    
    //-----------------------------------------------------------------------
    /**
     * <p>Converts the Character to a char throwing an exception for {@code null}.</p>
     * 
     * <pre>
     *   CharUtils.toChar(' ')  = ' '
     *   CharUtils.toChar('A')  = 'A'
     *   CharUtils.toChar(null) throws IllegalArgumentException
     * </pre>
     *
     * @param ch  the character to convert
     * @return the char value of the Character
     * @throws IllegalArgumentException if the Character is null
     */
    public static char toChar(Character ch) {
        if (ch == null) {
            throw new IllegalArgumentException("The Character must not be null");
        }
        return ch.charValue();
    }
    
    /**
     * <p>Converts the Character to a char handling {@code null}.</p>
     * 
     * <pre>
     *   CharUtils.toChar(null, 'X') = 'X'
     *   CharUtils.toChar(' ', 'X')  = ' '
     *   CharUtils.toChar('A', 'X')  = 'A'
     * </pre>
     *
     * @param ch  the character to convert
     * @param defaultValue  the value to use if the  Character is null
     * @return the char value of the Character or the default if null
     */
    public static char toChar(Character ch, char defaultValue) {
        if (ch == null) {
            return defaultValue;
        }
        return ch.charValue();
    }
    
    //-----------------------------------------------------------------------
    /**
     * <p>Converts the String to a char using the first character, throwing
     * an exception on empty Strings.</p>
     * 
     * <pre>
     *   CharUtils.toChar("A")  = 'A'
     *   CharUtils.toChar("BA") = 'B'
     *   CharUtils.toChar(null) throws IllegalArgumentException
     *   CharUtils.toChar("")   throws IllegalArgumentException
     * </pre>
     *
     * @param str  the character to convert
     * @return the char value of the first letter of the String
     * @throws IllegalArgumentException if the String is empty
     */
    public static char toChar(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("The String must not be empty");
        }
        return str.charAt(0);
    }
    
    /**
     * <p>Converts the String to a char using the first character, defaulting
     * the value on empty Strings.</p>
     * 
     * <pre>
     *   CharUtils.toChar(null, 'X') = 'X'
     *   CharUtils.toChar("", 'X')   = 'X'
     *   CharUtils.toChar("A", 'X')  = 'A'
     *   CharUtils.toChar("BA", 'X') = 'B'
     * </pre>
     *
     * @param str  the character to convert
     * @param defaultValue  the value to use if the  Character is null
     * @return the char value of the first letter of the String or the default if null
     */
    public static char toChar(String str, char defaultValue) {
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        return str.charAt(0);
    }
    
    //-----------------------------------------------------------------------
    /**
     * <p>Converts the character to the Integer it represents, throwing an
     * exception if the character is not numeric.</p>
     * 
     * <p>This method coverts the char '1' to the int 1 and so on.</p>
     *
     * <pre>
     *   CharUtils.toIntValue('3')  = 3
     *   CharUtils.toIntValue('A')  throws IllegalArgumentException
     * </pre>
     *
     * @param ch  the character to convert
     * @return the int value of the character
     * @throws IllegalArgumentException if the character is not ASCII numeric
     */
    public static int toIntValue(char ch) {
        if (isAsciiNumeric(ch) == false) {
            throw new IllegalArgumentException("The character " + ch + " is not in the range '0' - '9'");
        }
        return ch - 48;
    }
    
    /**
     * <p>Converts the character to the Integer it represents, throwing an
     * exception if the character is not numeric.</p>
     * 
     * <p>This method coverts the char '1' to the int 1 and so on.</p>
     *
     * <pre>
     *   CharUtils.toIntValue('3', -1)  = 3
     *   CharUtils.toIntValue('A', -1)  = -1
     * </pre>
     *
     * @param ch  the character to convert
     * @param defaultValue  the default value to use if the character is not numeric
     * @return the int value of the character
     */
    public static int toIntValue(char ch, int defaultValue) {
        if (isAsciiNumeric(ch) == false) {
            return defaultValue;
        }
        return ch - 48;
    }
    
    /**
     * <p>Converts the character to the Integer it represents, throwing an
     * exception if the character is not numeric.</p>
     * 
     * <p>This method coverts the char '1' to the int 1 and so on.</p>
     *
     * <pre>
     *   CharUtils.toIntValue('3')  = 3
     *   CharUtils.toIntValue(null) throws IllegalArgumentException
     *   CharUtils.toIntValue('A')  throws IllegalArgumentException
     * </pre>
     *
     * @param ch  the character to convert, not null
     * @return the int value of the character
     * @throws IllegalArgumentException if the Character is not ASCII numeric or is null
     */
    public static int toIntValue(Character ch) {
        if (ch == null) {
            throw new IllegalArgumentException("The character must not be null");
        }
        return toIntValue(ch.charValue());
    }
    
    /**
     * <p>Converts the character to the Integer it represents, throwing an
     * exception if the character is not numeric.</p>
     * 
     * <p>This method coverts the char '1' to the int 1 and so on.</p>
     *
     * <pre>
     *   CharUtils.toIntValue(null, -1) = -1
     *   CharUtils.toIntValue('3', -1)  = 3
     *   CharUtils.toIntValue('A', -1)  = -1
     * </pre>
     *
     * @param ch  the character to convert
     * @param defaultValue  the default value to use if the character is not numeric
     * @return the int value of the character
     */
    public static int toIntValue(Character ch, int defaultValue) {
        if (ch == null) {
            return defaultValue;
        }
        return toIntValue(ch.charValue(), defaultValue);
    }
    
    //-----------------------------------------------------------------------
    /**
     * <p>Converts the character to a String that contains the one character.</p>
     * 
     * <p>For ASCII 7 bit characters, this uses a cache that will return the
     * same String object each time.</p>
     *
     * <pre>
     *   CharUtils.toString(' ')  = " "
     *   CharUtils.toString('A')  = "A"
     * </pre>
     *
     * @param ch  the character to convert
     * @return a String containing the one specified character
     */
    public static String toString(char ch) {
        if (ch < 128) {
            return CHAR_STRING_ARRAY[ch];
        }
        return new String(new char[] {ch});
    }
    
    /**
     * <p>Converts the character to a String that contains the one character.</p>
     * 
     * <p>For ASCII 7 bit characters, this uses a cache that will return the
     * same String object each time.</p>
     * 
     * <p>If {@code null} is passed in, {@code null} will be returned.</p>
     *
     * <pre>
     *   CharUtils.toString(null) = null
     *   CharUtils.toString(' ')  = " "
     *   CharUtils.toString('A')  = "A"
     * </pre>
     *
     * @param ch  the character to convert
     * @return a String containing the one specified character
     */
    public static String toString(Character ch) {
        if (ch == null) {
            return null;
        }
        return toString(ch.charValue());
    }
    
    //--------------------------------------------------------------------------
    /**
     * <p>Converts the string to the Unicode format '\u0020'.</p>
     * 
     * <p>This format is the Java source code format.</p>
     *
     * <pre>
     *   CharUtils.unicodeEscaped(' ') = "\u0020"
     *   CharUtils.unicodeEscaped('A') = "\u0041"
     * </pre>
     * 
     * @param ch  the character to convert
     * @return the escaped Unicode string
     */
    public static String unicodeEscaped(char ch) {
        if (ch < 0x10) {
            return "\\u000" + Integer.toHexString(ch);
        } else if (ch < 0x100) {
            return "\\u00" + Integer.toHexString(ch);
        } else if (ch < 0x1000) {
            return "\\u0" + Integer.toHexString(ch);
        }
        return "\\u" + Integer.toHexString(ch);
    }
    
    /**
     * <p>Converts the string to the Unicode format '\u0020'.</p>
     * 
     * <p>This format is the Java source code format.</p>
     * 
     * <p>If {@code null} is passed in, {@code null} will be returned.</p>
     *
     * <pre>
     *   CharUtils.unicodeEscaped(null) = null
     *   CharUtils.unicodeEscaped(' ')  = "\u0020"
     *   CharUtils.unicodeEscaped('A')  = "\u0041"
     * </pre>
     * 
     * @param ch  the character to convert, may be null
     * @return the escaped Unicode string, null if null input
     */
    public static String unicodeEscaped(Character ch) {
        if (ch == null) {
            return null;
        }
        return unicodeEscaped(ch.charValue());
    }
    
    //--------------------------------------------------------------------------
    /**
     * <p>Checks whether the character is ASCII 7 bit.</p>
     *
     * <pre>
     *   CharUtils.isAscii('a')  = true
     *   CharUtils.isAscii('A')  = true
     *   CharUtils.isAscii('3')  = true
     *   CharUtils.isAscii('-')  = true
     *   CharUtils.isAscii('\n') = true
     *   CharUtils.isAscii('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if less than 128
     */
    public static boolean isAscii(char ch) {
        return ch < 128;
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit printable.</p>
     *
     * <pre>
     *   CharUtils.isAsciiPrintable('a')  = true
     *   CharUtils.isAsciiPrintable('A')  = true
     *   CharUtils.isAsciiPrintable('3')  = true
     *   CharUtils.isAsciiPrintable('-')  = true
     *   CharUtils.isAsciiPrintable('\n') = false
     *   CharUtils.isAsciiPrintable('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 32 and 126 inclusive
     */
    public static boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch < 127;
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit control.</p>
     *
     * <pre>
     *   CharUtils.isAsciiControl('a')  = false
     *   CharUtils.isAsciiControl('A')  = false
     *   CharUtils.isAsciiControl('3')  = false
     *   CharUtils.isAsciiControl('-')  = false
     *   CharUtils.isAsciiControl('\n') = true
     *   CharUtils.isAsciiControl('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if less than 32 or equals 127
     */
    public static boolean isAsciiControl(char ch) {
        return ch < 32 || ch == 127;
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit alphabetic.</p>
     *
     * <pre>
     *   CharUtils.isAsciiAlpha('a')  = true
     *   CharUtils.isAsciiAlpha('A')  = true
     *   CharUtils.isAsciiAlpha('3')  = false
     *   CharUtils.isAsciiAlpha('-')  = false
     *   CharUtils.isAsciiAlpha('\n') = false
     *   CharUtils.isAsciiAlpha('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 65 and 90 or 97 and 122 inclusive
     */
    public static boolean isAsciiAlpha(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit alphabetic upper case.</p>
     *
     * <pre>
     *   CharUtils.isAsciiAlphaUpper('a')  = false
     *   CharUtils.isAsciiAlphaUpper('A')  = true
     *   CharUtils.isAsciiAlphaUpper('3')  = false
     *   CharUtils.isAsciiAlphaUpper('-')  = false
     *   CharUtils.isAsciiAlphaUpper('\n') = false
     *   CharUtils.isAsciiAlphaUpper('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 65 and 90 inclusive
     */
    public static boolean isAsciiAlphaUpper(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit alphabetic lower case.</p>
     *
     * <pre>
     *   CharUtils.isAsciiAlphaLower('a')  = true
     *   CharUtils.isAsciiAlphaLower('A')  = false
     *   CharUtils.isAsciiAlphaLower('3')  = false
     *   CharUtils.isAsciiAlphaLower('-')  = false
     *   CharUtils.isAsciiAlphaLower('\n') = false
     *   CharUtils.isAsciiAlphaLower('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 97 and 122 inclusive
     */
    public static boolean isAsciiAlphaLower(char ch) {
        return ch >= 'a' && ch <= 'z';
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit numeric.</p>
     *
     * <pre>
     *   CharUtils.isAsciiNumeric('a')  = false
     *   CharUtils.isAsciiNumeric('A')  = false
     *   CharUtils.isAsciiNumeric('3')  = true
     *   CharUtils.isAsciiNumeric('-')  = false
     *   CharUtils.isAsciiNumeric('\n') = false
     *   CharUtils.isAsciiNumeric('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 48 and 57 inclusive
     */
    public static boolean isAsciiNumeric(char ch) {
        return ch >= '0' && ch <= '9';
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit numeric.</p>
     *
     * <pre>
     *   CharUtils.isAsciiAlphanumeric('a')  = true
     *   CharUtils.isAsciiAlphanumeric('A')  = true
     *   CharUtils.isAsciiAlphanumeric('3')  = true
     *   CharUtils.isAsciiAlphanumeric('-')  = false
     *   CharUtils.isAsciiAlphanumeric('\n') = false
     *   CharUtils.isAsciiAlphanumeric('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 48 and 57 or 65 and 90 or 97 and 122 inclusive
     */
    public static boolean isAsciiAlphanumeric(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9');
    }
    
}
