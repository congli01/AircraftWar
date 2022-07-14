package edu.hitsz.prop;

/**
 * 对炸弹道具做出反应的接口
 * @author LICONG
 */
public interface BombReactor {
        /**
         * 对炸弹道具的反应
         */
        public void bombReact();

        /**
         * 订阅者失效
         * @return
         */
        public boolean unSubed();
}
