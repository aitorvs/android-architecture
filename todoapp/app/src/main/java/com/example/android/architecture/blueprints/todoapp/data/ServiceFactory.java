package com.example.android.architecture.blueprints.todoapp.data;

import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Simple factory class to create instances of {@link Retrofit} services
 * In a real app we'd use DI to inject the instances of both {@link Retrofit} and the services
 */
class ServiceFactory {
    private static ServiceFactory instance;
    private final MockRetrofit mockRetrofit;

    private ServiceFactory() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://todo-ribs") // anything really
            .client(new OkHttpClient.Builder().build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())) // IO always
            .build();

        // FIXME this network behavior will be different for different flavors (prd and mock)
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(0);
        behavior.setVariancePercent(0);
        behavior.setDelay(200, TimeUnit.MILLISECONDS);

        // create the mock retrofit, we never use the real one as we don't have any BE
        mockRetrofit = new MockRetrofit.Builder(retrofit).networkBehavior(behavior).build();
    }

    private static ServiceFactory instance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    /**
     * @return Returns the {@link Task} remote {@link Retrofit} service
     */
    static TaskService taskService() {
        return instance().service();
    }

    /**
     * Returns a mock implementation of {@link TaskService}.
     * In a real app this would return a real implementation for some builds and a mock implementation for other builds
     *
     * @return implementation of {@link TaskService}
     */
    private TaskService service() {
        return new MockTaskService(mockRetrofit);
    }
}
