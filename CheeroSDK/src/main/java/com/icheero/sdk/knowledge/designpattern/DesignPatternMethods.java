package com.icheero.sdk.knowledge.designpattern;

import com.icheero.sdk.knowledge.designpattern.structural.bridge.Abstraction;
import com.icheero.sdk.knowledge.designpattern.structural.bridge.ConcreteImpA;
import com.icheero.sdk.knowledge.designpattern.structural.bridge.ConcreteImpB;
import com.icheero.sdk.knowledge.designpattern.structural.bridge.RefinedAbstraction;
import com.icheero.sdk.knowledge.designpattern.behavioral.command.Command;
import com.icheero.sdk.knowledge.designpattern.behavioral.command.ConcreteCommand;
import com.icheero.sdk.knowledge.designpattern.behavioral.command.Invoker;
import com.icheero.sdk.knowledge.designpattern.behavioral.command.Receiver;
import com.icheero.sdk.knowledge.designpattern.structural.composite.demo.ConcreteCompany;
import com.icheero.sdk.knowledge.designpattern.structural.composite.demo.FinanceDepartment;
import com.icheero.sdk.knowledge.designpattern.structural.composite.demo.HRDepartment;
import com.icheero.sdk.knowledge.designpattern.structural.composite.idea.Composite;
import com.icheero.sdk.knowledge.designpattern.structural.composite.idea.Leaf;
import com.icheero.sdk.knowledge.designpattern.structural.flyweight.demo.User;
import com.icheero.sdk.knowledge.designpattern.structural.flyweight.demo.WebSite;
import com.icheero.sdk.knowledge.designpattern.structural.flyweight.demo.WebSiteFactory;
import com.icheero.sdk.knowledge.designpattern.structural.flyweight.idea.Flyweight;
import com.icheero.sdk.knowledge.designpattern.structural.flyweight.idea.FlyweightFactory;
import com.icheero.sdk.knowledge.designpattern.structural.flyweight.idea.UnsharedConcreteFlyweight;
import com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.demo.Expression;
import com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.demo.Note;
import com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.demo.PlayContext;
import com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.demo.Scale;
import com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.idea.AbstractExpression;
import com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.idea.Context;
import com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.idea.NonterminalExpression;
import com.icheero.sdk.knowledge.designpattern.behavioral.interpreter.idea.TerminalExpression;
import com.icheero.sdk.knowledge.designpattern.behavioral.iterator.ConcreteAggregate;
import com.icheero.sdk.knowledge.designpattern.behavioral.iterator.ConcreteIterator;
import com.icheero.sdk.knowledge.designpattern.behavioral.iterator.Iterator;
import com.icheero.sdk.knowledge.designpattern.behavioral.mediator.Iraq;
import com.icheero.sdk.knowledge.designpattern.behavioral.mediator.SecurityCouncil;
import com.icheero.sdk.knowledge.designpattern.behavioral.mediator.USA;
import com.icheero.sdk.knowledge.designpattern.behavioral.responsibilitychain.GroupLeader;
import com.icheero.sdk.knowledge.designpattern.behavioral.responsibilitychain.SuperiorLeader;
import com.icheero.sdk.knowledge.designpattern.behavioral.responsibilitychain.WorkRequest;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo.Failing;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo.Man;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo.PersonStructure;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo.Success;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.demo.Woman;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.idea.ConcreteElementA;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.idea.ConcreteElementB;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.idea.ConcreteVisitorA;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.idea.ConcreteVisitorB;
import com.icheero.sdk.knowledge.designpattern.behavioral.visitor.idea.ObjectStructure;
import com.icheero.sdk.knowledge.designpattern.structural.proxy.dynamic.IStudent;
import com.icheero.sdk.knowledge.designpattern.structural.proxy.dynamic.OrdinaryStudent;
import com.icheero.sdk.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuochengyao on 2018/3/19.
 *
 * 设计模式六大原则：
 *
 * 1. 单一职责：就一个类而言，应该仅有一个引起它变化的原因。
 *
 * 2. 开放封闭：类、模块、函数等是可扩展（开放），但是不可修改（封闭）。
 *
 * 3. 里式替换：所有引用基类（父类）的地方，必须能透明地使用其子类的对象。
 * @description 在软件中将一个基类对象替换成其子类对象，程序将不会产生任何错误和异常；反之不成立。
 *
 * 4. 依赖倒置：高层模块不应该依赖底层模块，两者都应该依赖于抽象（抽象类 or 接口）；抽象不应该依赖于细节（继承抽象类 or 实现接口），细节应该依赖于抽象。
 * @description 模块间的依赖通过抽象发生，实现类之间不发生直接依赖关系
 *
 * 5. 迪米特原则（也叫 最少知识原则）：一个软件实体应当尽可能少的与其他实体发生相互作用。
 * @description 当一个模块发生修改时，尽可能的少影响其它模块：类之间松耦合；尽量降低类成员变量&函数的访问权限；对象之间引用降到最低。
 *
 * 6. 接口隔离：一个类对另一个类的依赖应该建立在最小的接口上。
 * @description 接口尽量小，但是要有限度；为依赖的接口的类定制服务，只暴露给调用的类它需要的方法，不需要的则隐藏；提高内聚，减少对外交互。
 *
 */


public class DesignPatternMethods
{
    private static final Class<DesignPatternMethods> TAG = DesignPatternMethods.class;

    public static void main(String[] args)
    {
        dynamicProxyStudent();
    }

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
        Abstraction ab = new RefinedAbstraction();
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

    public static void doVisitorIdea()
    {
        ObjectStructure o = new ObjectStructure();
        o.attach(new ConcreteElementA());
        o.attach(new ConcreteElementB());
        ConcreteVisitorA visitorA = new ConcreteVisitorA();
        ConcreteVisitorB visitorB = new ConcreteVisitorB();
        o.accept(visitorA);
        o.accept(visitorB);
    }

    public static void doVisitorDemo()
    {
        PersonStructure p = new PersonStructure();
        p.attach(new Man());
        p.attach(new Woman());
        Failing failing = new Failing();
        p.accept(failing);
        Success success = new Success();
        p.accept(success);
    }

    public static void dynamicProxyStudent()
    {
        IStudent ordinaryStudent = new OrdinaryStudent();
        ordinaryStudent.eat();
        ordinaryStudent.write();

        IStudent proxyStudent = (IStudent) Proxy.newProxyInstance(IStudent.class.getClassLoader(), new Class[]{IStudent.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
            {
                if (method.getName().equals("eat"))
                {
                    method.invoke(ordinaryStudent, args);
                    System.out.println("代理吃");
                    return null;
                }
                if (method.getName().equals("write"))
                {
                    method.invoke(ordinaryStudent, args);
                    System.out.println("代理写作文");
                    return null;
                }
                return null;
            }
        });
        proxyStudent.eat();
        proxyStudent.write();
    }
}
