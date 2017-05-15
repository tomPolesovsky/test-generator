package domainapp.dom.customer;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QCustomer extends PersistableExpressionImpl<Customer> implements PersistableExpression<Customer>
{
    public static final QCustomer jdoCandidate = candidate("this");

    public static QCustomer candidate(String name)
    {
        return new QCustomer(null, name, 5);
    }

    public static QCustomer candidate()
    {
        return jdoCandidate;
    }

    public static QCustomer parameter(String name)
    {
        return new QCustomer(Customer.class, name, ExpressionType.PARAMETER);
    }

    public static QCustomer variable(String name)
    {
        return new QCustomer(Customer.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;
    public final StringExpression surname;
    public final StringExpression email;

    public QCustomer(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
        this.surname = new StringExpressionImpl(this, "surname");
        this.email = new StringExpressionImpl(this, "email");
    }

    public QCustomer(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
        this.surname = new StringExpressionImpl(this, "surname");
        this.email = new StringExpressionImpl(this, "email");
    }
}
