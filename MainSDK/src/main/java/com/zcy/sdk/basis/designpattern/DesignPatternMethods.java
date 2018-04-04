package com.zcy.sdk.basis.designpattern;

import com.zcy.sdk.basis.designpattern.bridge.Abstraction;
import com.zcy.sdk.basis.designpattern.bridge.ConcreteImpA;
import com.zcy.sdk.basis.designpattern.bridge.ConcreteImpB;
import com.zcy.sdk.basis.designpattern.bridge.RefinedAbstruction;
import com.zcy.sdk.basis.designpattern.command.Command;
import com.zcy.sdk.basis.designpattern.command.ConcreteCommand;
import com.zcy.sdk.basis.designpattern.command.Invoker;
import com.zcy.sdk.basis.designpattern.command.Receiver;
import com.zcy.sdk.basis.designpattern.composite.demo.ConcreteCompany;
import com.zcy.sdk.basis.designpattern.composite.demo.FinanceDepartment;
import com.zcy.sdk.basis.designpattern.composite.demo.HRDepartment;
import com.zcy.sdk.basis.designpattern.composite.idea.Composite;
import com.zcy.sdk.basis.designpattern.composite.idea.Leaf;
import com.zcy.sdk.basis.designpattern.flyweight.demo.User;
import com.zcy.sdk.basis.designpattern.flyweight.demo.WebSite;
import com.zcy.sdk.basis.designpattern.flyweight.demo.WebSiteFactory;
import com.zcy.sdk.basis.designpattern.flyweight.idea.Flyweight;
import com.zcy.sdk.basis.designpattern.flyweight.idea.FlyweightFactory;
import com.zcy.sdk.basis.designpattern.flyweight.idea.UnsharedConcreteFlyweight;
import com.zcy.sdk.basis.designpattern.interpreter.demo.Expression;
import com.zcy.sdk.basis.designpattern.interpreter.demo.Note;
import com.zcy.sdk.basis.designpattern.interpreter.demo.PlayContext;
import com.zcy.sdk.basis.designpattern.interpreter.demo.Scale;
import com.zcy.sdk.basis.designpattern.interpreter.idea.AbstractExpression;
import com.zcy.sdk.basis.designpattern.interpreter.idea.Context;
import com.zcy.sdk.basis.designpattern.interpreter.idea.NonterminalExpression;
import com.zcy.sdk.basis.designpattern.interpreter.idea.TerminalExpression;
import com.zcy.sdk.basis.designpattern.iterator.ConcreteAggregate;
import com.zcy.sdk.basis.designpattern.iterator.ConcreteIterator;
import com.zcy.sdk.basis.designpattern.iterator.Iterator;
import com.zcy.sdk.basis.designpattern.mediator.Iraq;
import com.zcy.sdk.basis.designpattern.mediator.SecurityCouncil;
import com.zcy.sdk.basis.designpattern.mediator.USA;
import com.zcy.sdk.basis.designpattern.responsibility.GroupLeader;
import com.zcy.sdk.basis.designpattern.responsibility.SuperiorLeader;
import com.zcy.sdk.basis.designpattern.responsibility.WorkRequest;
import com.zcy.sdk.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public static void doCommand()
    {
        Receiver r = new Receiver();
        Command c = new ConcreteCommand(r);
        Invoker i = new Invoker();
        i.setCommand(c);
        i.execute();
    }

    public static void doResponsibilityChain()
    {
        GroupLeader gl = new GroupLeader("gl");
        SuperiorLeader sl = new SuperiorLeader("sl");
        sl.setLeader(gl);

        WorkRequest requestA = new WorkRequest();
        requestA.setType(WorkRequest.TYPE_HOLIDAY);
        requestA.setContent("zcy请假");
        requestA.setNumber(3);
        sl.doRequest(requestA);

        WorkRequest requestB = new WorkRequest();
        requestB.setType(WorkRequest.TYPE_MONEY);
        requestB.setContent("zcy加薪");
        requestB.setNumber(500);
        sl.doRequest(requestB);

        WorkRequest requestC = new WorkRequest();
        requestC.setType(WorkRequest.TYPE_MONEY);
        requestC.setContent("zcy加薪");
        requestC.setNumber(1000);
        sl.doRequest(requestC);

        WorkRequest requestD = new WorkRequest();
        requestD.setType(WorkRequest.TYPE_HOLIDAY);
        requestD.setContent("zcy请假");
        requestD.setNumber(1);
        sl.doRequest(requestD);
    }

    public static void doMediator()
    {
        SecurityCouncil SC = new SecurityCouncil();
        USA america = new USA(SC);
        Iraq iraq = new Iraq(SC);
        SC.setAmerica(america);
        SC.setIraq(iraq);
        america.declare("NO ZUO NO DIE!");
        iraq.declare("CAO LI LIANG!");
    }

    public static void doFlyWeightIdea()
    {
        int extrinsicState = 22;
        FlyweightFactory f = new FlyweightFactory();
        Flyweight fwX = f.getFlyweight("X");
        fwX.operation(--extrinsicState);
        Flyweight fwY = f.getFlyweight("Y");
        fwY.operation(--extrinsicState);
        Flyweight fwZ = f.getFlyweight("Z");
        fwZ.operation(--extrinsicState);
        Flyweight fwU = new UnsharedConcreteFlyweight();
        fwU.operation(--extrinsicState);
    }

    public static void doFlyWeightDemo()
    {
        WebSiteFactory f = new WebSiteFactory();
        WebSite ws0 = f.getWebSiteCategory("产品展示");
        ws0.setUser(new User("zcy"));
        WebSite ws1 = f.getWebSiteCategory("产品展示");
        ws1.setUser(new User("zjy"));
        WebSite ws2 = f.getWebSiteCategory("博客");
        ws2.setUser(new User("zcy"));
        WebSite ws3 = f.getWebSiteCategory("博客");
        ws3.setUser(new User("zjy"));
        Log.i(TAG, "网站分类总数：" + f.getWebSiteCount());
    }

    public static void doInterpreterIdea()
    {
        Context context = new Context();
        List<AbstractExpression> list = new ArrayList<>();
        list.add(new TerminalExpression());
        list.add(new NonterminalExpression());
        list.add(new TerminalExpression());
        list.add(new TerminalExpression());
        for (AbstractExpression exp : list)
        {
            exp.interpret(context);
        }
    }

    public static void doInterpreterDemo()
    {
        PlayContext context = new PlayContext();
        Log.i(TAG, "上海滩：");
        context.setText("O 2 E 0.5 G 0.5 A 3 E 0.5 G 0.5 D 3 E 0.5 G 0.5 A 0.5 A 0.5 O 3 C 1 O 2 A 0.5 G 1 C 0.5 E 0.5 D 3 ");
        Expression expression;
        try
        {
            while (context.getText().length() > 0)
            {
                String str = context.getText().substring(0, 1);
                switch (str)
                {
                    case "O":
                        expression = new Scale();
                        break;
                    case "C":
                    case "D":
                    case "E":
                    case "F":
                    case "G":
                    case "A":
                    case "B":
                    case "P":
                        expression = new Note();
                        break;
                    default:
                        expression = null;
                        break;
                }
                if (expression != null)
                    expression.interpret(context);
            }
        }
        catch (Exception e)
        {
            Log.i(TAG, e.toString());
        }
    }
}
