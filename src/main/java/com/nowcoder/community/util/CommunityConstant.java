package com.nowcoder.community.util;

public interface CommunityConstant {

    /**
     * Activation Succeed
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * Activation Repeat
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * Activation Fail
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * Default login ticket expired time
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     *  Extended login ticket expired time
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

}
