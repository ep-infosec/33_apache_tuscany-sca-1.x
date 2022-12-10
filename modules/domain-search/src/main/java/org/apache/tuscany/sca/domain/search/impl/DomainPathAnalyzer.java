/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

/**
 * 
 * @version $Rev$ $Date$
 */
public class DomainPathAnalyzer extends Analyzer {

    final public static char PATH_START = '\u0001';

    final public static char PATH_SEPARATOR = '\u0002';

    final public static char TYPE_SEPARATOR = '\u0003';

    final public static char URI_SEPARATOR = '\u0004';

    final public static char ARCHIVE_SEPARATOR = '!';

    static class DomainPathTokenizer extends Tokenizer {

        private int offset = 0, bufferIndex = 0, dataLen = 0;
        private static final int MAX_WORD_LEN = 1024;
        private static final int IO_BUFFER_SIZE = 4096;
        private final char[] ioBuffer = new char[IO_BUFFER_SIZE];
        private boolean typeCharFound = false;
        private boolean uriCharFound = false;

        public DomainPathTokenizer(Reader reader) {
            super(reader);
        }

        @Override
        public void reset() throws IOException {
            super.reset();

            typeCharFound = false;
            uriCharFound = false;

        }

        @Override
        public void reset(Reader input) throws IOException {
            super.reset(input);

            uriCharFound = false;
            typeCharFound = false;

        }

        @Override
        public Token next(Token reusableToken) throws IOException {
            assert reusableToken != null;
            reusableToken.clear();
            int length = 0;
            int start = bufferIndex;
            char[] buffer = reusableToken.termBuffer();

            boolean lowercaseCharFound = false;
            boolean digitFound = false;

            while (true) {

                if (bufferIndex >= dataLen) {
                    offset += dataLen;
                    int incr;

                    if (lowercaseCharFound || length == 0) {
                        incr = 0;

                    } else {
                        incr = 2;
                        ioBuffer[0] = ioBuffer[bufferIndex - 1];
                        ioBuffer[1] = ioBuffer[bufferIndex];

                    }

                    dataLen = input.read(ioBuffer, incr, ioBuffer.length - incr);
                    if (dataLen == -1) {
                        if (length > 0)
                            break;
                        else
                            return null;
                    }
                    bufferIndex = incr;
                    dataLen += incr;

                }

                final char c = ioBuffer[bufferIndex++];
                boolean breakChar = true;
                boolean includeChar = false;

                if (c == PATH_START || c == PATH_SEPARATOR) {

                    if (length == 0) {
                        includeChar = true;

                    } else {
                        bufferIndex--;
                    }

                    typeCharFound = false;
                    uriCharFound = false;

                } else if (c == TYPE_SEPARATOR && !typeCharFound || c == URI_SEPARATOR && !uriCharFound) {
                    length = 0;
                    breakChar = false;
                    lowercaseCharFound = false;
                    digitFound = false;

                } else {

                    if (Character.isDigit(c)) {

                        if (digitFound || length == 0) {
                            breakChar = false;
                            digitFound = true;

                        } else {
                            bufferIndex--;
                        }

                        // TODO: normalize accent, it does not index accents for
                        // now
                    } else if (c >= 65 && c <= 90 || c >= 97 && c <= 122) {

                        if (digitFound) {
                            bufferIndex--;

                        } else if (Character.isLowerCase(c)) {

                            if (!(lowercaseCharFound || length <= 1)) {
                                length--;
                                bufferIndex -= 2;

                            } else {
                                lowercaseCharFound = true;
                                breakChar = false;

                            }

                        } else if (!lowercaseCharFound) { // && uppercase
                            breakChar = false;

                        } else {
                            bufferIndex--;
                        }

                    }

                }

                if (!breakChar || includeChar) {

                    if (length == 0) // start of token
                        start = offset + bufferIndex - 1;
                    else if (length == buffer.length)
                        buffer = reusableToken.resizeTermBuffer(1 + length);

                    if (c == TYPE_SEPARATOR && !typeCharFound) {
                        typeCharFound = true;

                    } else if (c == URI_SEPARATOR && !uriCharFound) {
                        typeCharFound = true;

                    } else {
                        buffer[length++] = Character.toLowerCase(c); // buffer
                        // it,
                        // normalized
                    }

                    if (length == MAX_WORD_LEN || (breakChar && length > 0)) // buffer
                        // overflow!
                        break;

                } else if (length > 0) {// at non-Letter w/ chars

                    break; // return 'em

                }

            }

            reusableToken.setTermLength(length);
            reusableToken.setStartOffset(start);
            reusableToken.setEndOffset(start + length);

            return reusableToken;

        }
    }

    public TokenStream tokenStream(String fieldName, Reader reader) {
        return new DomainPathTokenizer(reader);
    }

    public TokenStream reusableTokenStream(String fieldName, Reader reader) throws IOException {
        Tokenizer tokenizer = (Tokenizer)getPreviousTokenStream();
        if (tokenizer == null) {
            tokenizer = new DomainPathTokenizer(reader);
            setPreviousTokenStream(tokenizer);
        } else
            tokenizer.reset(reader);
        return tokenizer;
    }

}
