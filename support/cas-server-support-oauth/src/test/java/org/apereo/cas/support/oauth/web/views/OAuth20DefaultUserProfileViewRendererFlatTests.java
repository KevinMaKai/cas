package org.apereo.cas.support.oauth.web.views;

import org.apereo.cas.support.oauth.web.AbstractOAuth20Tests;
import org.apereo.cas.ticket.accesstoken.AccessToken;
import org.apereo.cas.util.CollectionUtils;

import lombok.val;
import org.hjson.JsonValue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * This is {@link OAuth20DefaultUserProfileViewRendererFlatTests}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
@TestPropertySource(properties = "cas.authn.oauth.userProfileViewType=FLAT")
public class OAuth20DefaultUserProfileViewRendererFlatTests extends AbstractOAuth20Tests {

    @Autowired
    @Qualifier("oauthUserProfileViewRenderer")
    private OAuth20UserProfileViewRenderer oauthUserProfileViewRenderer;

    @Test
    public void verifyNestedOption() {
        val map = CollectionUtils.wrap(OAuth20UserProfileViewRenderer.MODEL_ATTRIBUTE_ID, "cas",
            OAuth20UserProfileViewRenderer.MODEL_ATTRIBUTE_ATTRIBUTES,
            CollectionUtils.wrap("email", "cas@example.org", "name", "Test"),
            "something", CollectionUtils.wrapList("something"));
        val json = oauthUserProfileViewRenderer.render((Map) map, mock(AccessToken.class));
        val value = JsonValue.readJSON(json).asObject();
        assertNotNull(value.get(OAuth20UserProfileViewRenderer.MODEL_ATTRIBUTE_ID));
        assertNotNull(value.get("email"));
        assertNotNull(value.get("name"));
        assertNull(value.get(OAuth20UserProfileViewRenderer.MODEL_ATTRIBUTE_ATTRIBUTES));
    }

}
