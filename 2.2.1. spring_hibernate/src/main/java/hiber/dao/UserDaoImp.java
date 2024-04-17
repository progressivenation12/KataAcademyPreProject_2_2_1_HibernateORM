package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {


    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    // JOIN FETCH user.car - позволит загрузить связанные сущности Car для каждого пользователя в рамках одного запроса,
    // что должно предотвращать проблему N+1.
    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("SELECT user FROM User user JOIN FETCH user.car");
        return query.getResultList();
    }

    // JOIN FETCH - гарантирует, что все User и связаные с ним Car будут загружены в рамках одного запроса,
    // что должно предотвращать проблему N+1.
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUserByCarModel(String model, int series) {
        String HQL = "SELECT DISTINCT user FROM User user JOIN FETCH user.car car WHERE car.model = :model AND car.series = :series";

        TypedQuery<User> userTypedQuery = sessionFactory.openSession().createQuery(HQL);

        userTypedQuery.setParameter("model", model);
        userTypedQuery.setParameter("series", series);

        return userTypedQuery.getResultList();
    }
}
