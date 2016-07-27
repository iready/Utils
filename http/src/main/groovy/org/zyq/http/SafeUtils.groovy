package org.zyq.http

/**
 * Created by Administrator on 2016/5/5.
 */
class SafeUtils {

    public void makeItSure(Closure closure, Closure error) {
        try {
            closure();
        } catch (Exception e) {
            if (error != null)
                error(e)
            makeItSure(closure, error)
        }
    }
}
