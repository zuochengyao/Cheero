package com.icheero.sdk.core.xml;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

abstract class AbsXmlReader
{
    protected Class TAG;

    protected static final String XML_MENU = "menu";
    protected static final String XML_GROUP = "group";
    protected static final String XML_ITEM = "item";

    protected Context mContext;
    protected XmlResourceParser mParser;

    AbsXmlReader(Context context, int menuRes)
    {
        this.TAG = getClass();
        this.mContext = context;
        init();
        try
        {
            mParser = mContext.getResources().getLayout(menuRes);
            AttributeSet attrs = Xml.asAttributeSet(mParser);
            parseXml(mParser, attrs);
        }
        catch (XmlPullParserException e)
        {
            throw new InflateException("Error inflating menu XML", e);
        }
        catch (IOException e)
        {
            throw new InflateException("Error inflating menu XML", e);
        }
        finally
        {
            if (mParser != null)
                mParser.close();
        }
    }

    protected void parseXml(XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException
    {
        int eventType = parser.getEventType();
        String tagName;
        String unknownTagName = null;
        boolean lookingForEndOfUnknownTag = false;
        boolean reachedEndOfMenu = false;
        boolean isFirstStartTag = true;
        while (!reachedEndOfMenu)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                {
                    break;
                }
                case XmlPullParser.START_TAG:
                {
                    if (lookingForEndOfUnknownTag)
                        break;
                    tagName = parser.getName();
                    if (isFirstStartTag)
                    {
                        if (tagName.equals(XML_MENU))
                            isFirstStartTag = false;
                        else
                            throw new RuntimeException("Not menu res, got " + tagName);
                    }
                    else
                    {
                        if (tagName.equals(XML_GROUP))
                            parseGroupStart(attrs);
                        else if (tagName.equals(XML_ITEM))
                            parseItemStart(attrs);
                        else
                        {
                            lookingForEndOfUnknownTag = true;
                            unknownTagName = tagName;
                        }
                    }
                    break;
                }
                case XmlPullParser.END_TAG:
                {
                    tagName = parser.getName();
                    if (tagName.equals(XML_MENU))
                        reachedEndOfMenu = true;
                    else if(tagName.equals(XML_GROUP))
                        parseGroupEnd(attrs);
                    else if(tagName.equals(XML_ITEM))
                        parseItemEnd(attrs);
                    else if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName))
                    {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                    }
                    break;
                }
                case XmlPullParser.END_DOCUMENT:
                    throw new RuntimeException("Unexpected end of document");
            }
            eventType = parser.next();
        }
    }

    protected abstract void init();

    protected abstract void parseGroupStart(AttributeSet attrs);

    protected abstract void parseGroupEnd(AttributeSet attrs);

    protected abstract void parseItemStart(AttributeSet attrs);

    protected abstract void parseItemEnd(AttributeSet attrs);
}
