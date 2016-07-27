package org.zyq.qq

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class test {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void bs(String one, Closure closure) {

    }

    public static void main(String[] args) {
        " {\"sid\":\"2J4mN31  NqVbIggeYhu6Iw9WtjwN7yl3a8012660201==\"".find("\"sid\":\"([0-9a-zA-Z= ]+)\"", {
            println it[1]
        })
    }
}
