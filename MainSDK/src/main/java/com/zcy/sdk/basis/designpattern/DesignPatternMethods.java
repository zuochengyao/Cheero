package com.zcy.sdk.basis.designpattern;

import com.zcy.sdk.basis.designpattern.bridge.Abstraction;
import com.zcy.sdk.basis.designpattern.bridge.ConcreteImpA;
import com.zcy.sdk.basis.designpattern.bridge.ConcreteImpB;
import com.zcy.sdk.basis.designpattern.bridge.RefinedAbstruction;
import com.zcy.sdk.basis.designpattern.composite.demo.ConcreteCompany;
import com.zcy.sdk.basis.designpattern.composite.demo.FinanceDepartment;
import com.zcy.sdk.basis.designpattern.composite.demo.HRDepartment;
import com.zcy.sdk.basis.designpattern.composite.idea.Composite;
import com.zcy.sdk.basis.designpattern.composite.idea.Leaf;
import com.zcy.sdk.basis.designpattern.iterator.ConcreteAggregate;
import com.zcy.sdk.basis.designpattern.iterator.ConcreteIterator;
import com.zcy.sdk.basis.designpattern.iterator.Iterator;
import com.zcy.sdk.util.Log;

/**
 * Created by zuochengyao on 2018/3/19.
 */

@SuppressWarnings("unused")
public class DesignPatternMethods
{
    private static final Class<DesignPatternMethods> TAG = DesignPatternMethods.class;

    public static void doCompositeIdea()
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

    public static void doCompositeDemo()
    {
        ConcreteCompany root = new ConcreteCompany("YaoTech-北京总部");
        root.add(new HRDepartment("总公司人力资源部"));
        root.add(new FinanceDepartment("总公司财务部"));

        ConcreteCompany compHD = new ConcreteCompany("YaoTech-华东分部（上海）");
        compHD.add(new HRDepartment("华东分部（上海）人力资源部"));
        compHD.add(new FinanceDepartment("华东分部（上海）财务部"));
        root.add(compHD);

        ConcreteCompany compNJC = new ConcreteCompany("YaoTech-南京办事处");
        compNJC.add(new HRDepartment("南京办事处人力资源部"));
        compNJC.add(new FinanceDepartment("南京办事处财务部"));
        compHD.add(compNJC);

        ConcreteCompany compHZC = new ConcreteCompany("YaoTech-杭州办事处");
        compHZC.add(new HRDepartment("杭州办事处人力资源部"));
        compHZC.add(new FinanceDepartment("杭州办事处财务部"));
        compHD.add(compHZC);

        Log.e(TAG, "\n 结构图：");
        root.display(1);
        Log.e(TAG, "\n 职责：");
        root.lineOfDuty();
    }

    public static void doIterator()
    {
        ConcreteAggregate c = new ConcreteAggregate();
        c.setItem(0, "Rockets");
        c.setItem(1, "Lakers");
        c.setItem(2, "Thunder");

        Iterator i = new ConcreteIterator(c);
        Object item = i.first();
        while (!i.isFinish())
        {
            Log.i(TAG, String.format("%s win!", i.currentItem()));
            i.next();
        }
    }

    public static void doBridge()
    {
        Abstraction ab = new RefinedAbstruction();
        ab.setImplementor(new ConcreteImpA());
        ab.operation();
        ab.setImplementor(new ConcreteImpB());
        ab.operation();
    }
}
