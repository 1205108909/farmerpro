package com.phenix.util;

import com.phenix.data.Security;
import org.jdom2.Element;
import org.junit.Assert;
import org.junit.Test;

public class ConfigMatchCriteriaTest {
    Element ele1 = Util.readStrAsXml("<Security>" +
            "<symbol>000300.sh|000905.sh</symbol>" +
            "<type>INDEX</type>" +
            "</Security>");

    Element ele2 = Util.readStrAsXml("<Security>" +
            "<symbol>000300.sh|000905.sh</symbol>" +
            "<type>STOCK</type>" +
            "</Security>");

    Element ele3 = Util.readStrAsXml("<Security>" +
            "<symbol>000888.sh</symbol>" +
            "<type>INDEX</type>" +
            "</Security>");

    @Test
    public void match() {
        ConfigMatchCriteria m1 = new ConfigMatchCriteria(0, ele1);
        ConfigMatchCriteria m2 = new ConfigMatchCriteria(0, ele2);
        ConfigMatchCriteria m3 = new ConfigMatchCriteria(0, ele3);

        Assert.assertTrue(m1.match(Security.INDEX_HS300));
        Assert.assertTrue(m1.match(Security.INDEX_ZZ500));
        Assert.assertFalse(m1.match(Security.INDEX_SZ50));
        Assert.assertFalse(m2.match(Security.INDEX_HS300));

        Assert.assertFalse(m3.match(Security.INDEX_HS300));
        Assert.assertFalse(m3.match(Security.INDEX_ZZ500));
    }
}