/**
 * Copyright 2012 John W. Krupansky d/b/a Base Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dcc.agent.server.service.script.parser.tokenizer;

import dcc.agent.server.service.script.parser.tokenizer.token.AsteriskEqualOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.AsteriskOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.AtSignOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.BitwiseAndEqualOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.BitwiseAndOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.BitwiseOrEqualOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.BitwiseOrOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.BooleanKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.BreakKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ByteKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.CaretOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.CaseKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.CatchKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.CharKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ColonOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.CommaOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ContinueKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.DateKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.DefaultKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.DoKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.DollarSignOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.DoubleKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ElseKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.EqualOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.EqualsOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ExclusiveOrEqualOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.FalseToken;
import dcc.agent.server.service.script.parser.tokenizer.token.FloatKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.FloatToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ForKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.GreaterEqualsOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.GreaterGreaterGreaterOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.GreaterGreaterOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.GreaterOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.IdentifierToken;
import dcc.agent.server.service.script.parser.tokenizer.token.IfKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.IntKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.IntegerKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.IntegerToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LeftBraceOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LeftParenthesisOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LeftSquareBracketOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LessEqualsOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LessLessOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LessOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ListKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LogicalAndOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LogicalNotOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LogicalOrOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.LongKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.MapKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.MinusEqualOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.MinusMinusOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.MinusOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.MoneyKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.NotEqualsOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.NowKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.NullToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ObjectKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.PercentSignOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.PeriodOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.PlusEqualOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.PlusOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.PlusPlusOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.PoundSignOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.QuestionMarkOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ReturnKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.RightBraceOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.RightParenthesisOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.RightSquareBracketOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.SemicolonOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ShortKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.SlashEqualOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.SlashOperatorToken;
import dcc.agent.server.service.script.parser.tokenizer.token.StringKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.StringToken;
import dcc.agent.server.service.script.parser.tokenizer.token.SwitchKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.ThrowKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.Token;
import dcc.agent.server.service.script.parser.tokenizer.token.TrueToken;
import dcc.agent.server.service.script.parser.tokenizer.token.TryKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.WebKeywordToken;
import dcc.agent.server.service.script.parser.tokenizer.token.WhileKeywordToken;

public class Tokenizer {
    public String s;
    public TokenList tokenList;

    public Tokenizer() {
    }

    public Tokenizer(String s) throws TokenizerException {
        tokenizeString(s);
    }

    public TokenList tokenizeString(String s) throws TokenizerException {
        if (s == null) {
            tokenList = null;
            return null;
        }

        tokenList = new TokenList();

        Characterizer czer = new Characterizer(s);
        char ch;
        while ((ch = czer.getCharNonBlank()) != 0) {
            Token token;
            if (czer.isIdentifierStart) {
                StringBuffer sbuf = new StringBuffer();
                do {
                    sbuf.append(ch);
                } while ((ch = czer.getNextChar()) != 0 && czer.isIdentifier);
                String identifier = sbuf.toString();
                if (identifier.equals("null"))
                    token = new NullToken();
                else if (identifier.equals("true"))
                    token = new TrueToken();
                else if (identifier.equals("false"))
                    token = new FalseToken();
                else if (identifier.equals("now"))
                    token = new NowKeywordToken();
                else if (identifier.equals("object"))
                    token = new ObjectKeywordToken();
                else if (identifier.equals("boolean"))
                    token = new BooleanKeywordToken();
                else if (identifier.equals("int"))
                    token = new IntKeywordToken();
                else if (identifier.equals("integer"))
                    token = new IntegerKeywordToken();
                else if (identifier.equals("long"))
                    token = new LongKeywordToken();
                else if (identifier.equals("byte"))
                    token = new ByteKeywordToken();
                else if (identifier.equals("short"))
                    token = new ShortKeywordToken();
                else if (identifier.equals("char"))
                    token = new CharKeywordToken();
                else if (identifier.equals("string"))
                    token = new StringKeywordToken();
                else if (identifier.equals("float"))
                    token = new FloatKeywordToken();
                else if (identifier.equals("double"))
                    token = new DoubleKeywordToken();
                else if (identifier.equals("date"))
                    token = new DateKeywordToken();
                else if (identifier.equals("money"))
                    token = new MoneyKeywordToken();
                else if (identifier.equals("list"))
                    token = new ListKeywordToken();
                else if (identifier.equals("map"))
                    token = new MapKeywordToken();
                else if (identifier.equals("web"))
                    token = new WebKeywordToken();
                else if (identifier.equals("if"))
                    token = new IfKeywordToken();
                else if (identifier.equals("else"))
                    token = new ElseKeywordToken();
                else if (identifier.equals("for"))
                    token = new ForKeywordToken();
                else if (identifier.equals("while"))
                    token = new WhileKeywordToken();
                else if (identifier.equals("do"))
                    token = new DoKeywordToken();
                else if (identifier.equals("break"))
                    token = new BreakKeywordToken();
                else if (identifier.equals("continue"))
                    token = new ContinueKeywordToken();
                else if (identifier.equals("return"))
                    token = new ReturnKeywordToken();
                else if (identifier.equals("switch"))
                    token = new SwitchKeywordToken();
                else if (identifier.equals("case"))
                    token = new CaseKeywordToken();
                else if (identifier.equals("default"))
                    token = new DefaultKeywordToken();
                else if (identifier.equals("try"))
                    token = new TryKeywordToken();
                else if (identifier.equals("catch"))
                    token = new CatchKeywordToken();
                else if (identifier.equals("throw"))
                    token = new ThrowKeywordToken();
                else if (identifier.equals("new"))
                    token = new NewKeywordToken();
                else
                    token = new IdentifierToken(identifier);
            } else if (czer.isDigit) {
                StringBuffer sbuf = new StringBuffer();
                boolean sawDot = false;
                do {
                    sbuf.append(ch);
                    if (ch == '.')
                        if (sawDot)
                            throw new TokenizerException("Extra dot in number at offset " + czer.nextCharIndex);
                        else
                            sawDot = true;
                } while ((ch = czer.getNextChar()) != 0 && (czer.isDigit || ch == '.'));
                String numberString = sbuf.toString();
                if (sawDot)
                    token = new FloatToken(numberString);
                else
                    token = new IntegerToken(numberString);
            } else if (ch == '.') {
                if ((ch = czer.getNextChar()) != 0 && czer.isDigit) {
                    StringBuffer sbuf = new StringBuffer(".");
                    boolean sawDot = true;
                    do {
                        sbuf.append(ch);
                        if (ch == '.')
                            if (sawDot)
                                throw new TokenizerException("Extra dot in number at offset " + czer.nextCharIndex);
                            else
                                sawDot = true;
                    } while ((ch = czer.getNextChar()) != 0 && (czer.isDigit || ch == '.'));
                    String numberString = sbuf.toString();
                    token = new FloatToken(numberString);
                } else
                    token = new PeriodOperatorToken();
            } else if (ch == '"') {
                StringBuffer sbuf = new StringBuffer();
                int startingOffset = czer.nextCharIndex;
                if ((ch = czer.getNextChar()) != 0 && ch != '"') {
                    do {
                        if (ch == '\\') {
                            ch = czer.getNextChar();
                            if (ch == 0)
                                throw new TokenizerException("Trailing backslash");

                            // Convert \n, \r, \t to proper codes
                            if (ch == 'n')
                                ch = '\n';
                            else if (ch == 'r')
                                ch = '\r';
                            else if (ch == '\t')
                                ch = '\t';
                        }
                        sbuf.append(ch);
                    } while ((ch = czer.getNextChar()) != 0 && ch != '"');
                }
                if (ch != '"')
                    throw new TokenizerException("Unterminated quoted string starting at offset " + startingOffset);
                String string = sbuf.toString();
                token = new StringToken(string);
                if (ch == '"')
                    czer.skipChar();
            } else if (ch == '\'') {
                StringBuffer sbuf = new StringBuffer();
                int startingOffset = czer.nextCharIndex;
                if ((ch = czer.getNextChar()) != 0 && ch != '\'') {
                    do {
                        if (ch == '\\') {
                            ch = czer.getNextChar();
                            if (ch == 0)
                                throw new TokenizerException("Trailing backslash");

                            // Convert \n, \r, \t to proper codes
                            if (ch == 'n')
                                ch = '\n';
                            else if (ch == 'r')
                                ch = '\r';
                            else if (ch == '\t')
                                ch = '\t';
                        }
                        sbuf.append(ch);
                    } while ((ch = czer.getNextChar()) != 0 && ch != '\'');
                }
                if (ch != '\'')
                    throw new TokenizerException("Unterminated quoted string starting at offset " + startingOffset);
                String string = sbuf.toString();
                token = new StringToken(string);
                if (ch == '\'')
                    czer.skipChar();
            } else if (ch == '=') {
                if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new EqualsOperatorToken();
                } else
                    token = new EqualOperatorToken();
                czer.skipChar();
            } else if (ch == '>') {
                if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new GreaterEqualsOperatorToken();
                } else if (czer.peekNextChar() == '>') {
                    czer.skipChar();
                    if (czer.peekNextChar() == '>') {
                        czer.skipChar();
                        token = new GreaterGreaterGreaterOperatorToken();
                    } else
                        token = new GreaterGreaterOperatorToken();
                } else
                    token = new GreaterOperatorToken();
                czer.skipChar();
            } else if (ch == '<') {
                if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new LessEqualsOperatorToken();
                } else if (czer.peekNextChar() == '<') {
                    czer.skipChar();
                    token = new LessLessOperatorToken();
                } else
                    token = new LessOperatorToken();
                czer.skipChar();
            } else if (ch == '&') {
                if (czer.peekNextChar() == '&') {
                    czer.skipChar();
                    token = new LogicalAndOperatorToken();
                } else if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new BitwiseAndEqualOperatorToken();
                } else
                    token = new BitwiseAndOperatorToken();
                czer.skipChar();
            } else if (ch == '|') {
                if (czer.peekNextChar() == '|') {
                    czer.skipChar();
                    token = new LogicalOrOperatorToken();
                } else if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new BitwiseOrEqualOperatorToken();
                } else
                    token = new BitwiseOrOperatorToken();
                czer.skipChar();
            } else if (ch == '!') {
                if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new NotEqualsOperatorToken();
                } else
                    token = new LogicalNotOperatorToken();
                czer.skipChar();
            } else if (ch == '^') {
                if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new ExclusiveOrEqualOperatorToken();
                } else
                    token = new CaretOperatorToken();
                czer.skipChar();
            } else if (ch == '@') {
                token = new AtSignOperatorToken();
                czer.skipChar();
            } else if (ch == '#') {
                token = new PoundSignOperatorToken();
                czer.skipChar();
            } else if (ch == '$') {
                token = new DollarSignOperatorToken();
                czer.skipChar();
            } else if (ch == '%') {
                token = new PercentSignOperatorToken();
                czer.skipChar();
            } else if (ch == '*') {
                if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new AsteriskEqualOperatorToken();
                } else
                    token = new AsteriskOperatorToken();
                czer.skipChar();
            } else if (ch == '(') {
                token = new LeftParenthesisOperatorToken();
                czer.skipChar();
            } else if (ch == ')') {
                token = new RightParenthesisOperatorToken();
                czer.skipChar();
            } else if (ch == '-') {
                char ch2 = czer.peekNextChar();
                if (ch2 == '=') {
                    czer.skipChar();
                    token = new MinusEqualOperatorToken();
                    czer.skipChar();
                } else if (ch2 == '-') {
                    czer.skipChar();
                    token = new MinusMinusOperatorToken();
                    czer.skipChar();
                } else if (Character.isDigit(ch2)) {
                    StringBuffer sbuf = new StringBuffer("-");
                    boolean sawDot = false;
                    ch = czer.getNextChar();
                    do {
                        sbuf.append(ch);
                        if (ch == '.')
                            if (sawDot)
                                throw new TokenizerException("Extra dot in number at offset " + czer.nextCharIndex);
                            else
                                sawDot = true;
                    } while ((ch = czer.getNextChar()) != 0 && (czer.isDigit || ch == '.'));
                    String numberString = sbuf.toString();
                    if (sawDot)
                        token = new FloatToken(numberString);
                    else
                        token = new IntegerToken(numberString);
                } else {
                    token = new MinusOperatorToken();
                    czer.skipChar();
                }
            } else if (ch == '+') {
                if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new PlusEqualOperatorToken();
                } else if (czer.peekNextChar() == '+') {
                    czer.skipChar();
                    token = new PlusPlusOperatorToken();
                } else
                    token = new PlusOperatorToken();
                czer.skipChar();
            } else if (ch == '[') {
                token = new LeftSquareBracketOperatorToken();
                czer.skipChar();
            } else if (ch == ']') {
                token = new RightSquareBracketOperatorToken();
                czer.skipChar();
            } else if (ch == '{') {
                token = new LeftBraceOperatorToken();
                czer.skipChar();
            } else if (ch == '}') {
                token = new RightBraceOperatorToken();
                czer.skipChar();
            } else if (ch == ',') {
                token = new CommaOperatorToken();
                czer.skipChar();
            } else if (ch == ':') {
                token = new ColonOperatorToken();
                czer.skipChar();
            } else if (ch == ';') {
                token = new SemicolonOperatorToken();
                czer.skipChar();
            } else if (ch == '?') {
                token = new QuestionMarkOperatorToken();
                czer.skipChar();
            } else if (ch == '/') {
                if (czer.peekNextChar() == '/') {
                    // Skip //-style comment
                    czer.skipChar();
                    while ((ch = czer.getNextChar()) != 0 && ch != '\n') {
                    }
                    token = null;
                } else if (czer.peekNextChar() == '*') {
                    // Skip /**/-style comment
                    int startingOffset = czer.nextCharIndex;
                    czer.skipChar();
                    while ((ch = czer.getNextChar()) != 0) {
                        if (ch == '*')
                            if ((ch = czer.peekNextChar()) == '/') {
                                czer.skipChar();
                                break;
                            }
                    }
                    if (ch == 0)
                        throw new TokenizerException("Unterminated /*...*/ comment starting at offset " + startingOffset);
                    token = null;
                } else if (czer.peekNextChar() == '=') {
                    czer.skipChar();
                    token = new SlashEqualOperatorToken();
                } else
                    token = new SlashOperatorToken();
                czer.skipChar();
            } else {
                throw new TokenizerException("Unknown token starting at offset " + czer.nextCharIndex + " for char '" + ch + "'");
            }

            // Add token to list
            if (token != null)
                tokenList.add(token);
        }
        return tokenList;
    }

}
