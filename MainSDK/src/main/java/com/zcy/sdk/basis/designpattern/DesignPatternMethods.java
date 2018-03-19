package com.zcy.sdk.basis.designpattern;

import com.zcy.sdk.basis.designpattern.composite.Composite;
import com.zcy.sdk.basis.designpattern.composite.Leaf;

/**
 * Created by zuochengyao on 2018/3/19.
 */

public class DesignPatternMethods
{
    public static void doComposite()
    {
        Composite root = new Composite("root");
        root.add(new Leaf("Leaf A"));
        root.add(new Leaf("Leaf B"));

        Composite compX = new Composite("Composite X");
        compX.add(new Leaf("Leaf XA"));
        compX.add(new Leaf("Leaf XB"));
        root.add(compX);

        Composite compY = new Composite("Composite Y");
        compY.add(new Leaf("Leaf YA"));
        compY.add(new Leaf("Leaf YB"));
        compX.add(compY);

        root.add(new Leaf("Leaf C"));

        Leaf leaf = new Leaf("Leaf D");
        root.add(leaf);
        root.remove(leaf);
        root.display(1);
    }
}
