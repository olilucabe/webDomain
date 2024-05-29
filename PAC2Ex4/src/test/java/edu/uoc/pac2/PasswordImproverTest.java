package edu.uoc.pac2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordImproverTest {

    @Test
    public void testReplaceVowels() {
        assertEquals("p4ssw0rd", PasswordImprover.replaceVowels("password"));
        assertEquals("P4ssw0rD", PasswordImprover.replaceVowels("PassworD"));
        assertEquals("w34k_p4ssw0rd", PasswordImprover.replaceVowels("weak_password"));
        assertEquals("w34k_p4ssW0rd", PasswordImprover.replaceVowels("wEAk_pAssWOrd"));
        assertEquals("20c_p4ssw0rd", PasswordImprover.replaceVowels("uoc_password"));
        assertEquals("my_p3rf3ct_s3cr3t", PasswordImprover.replaceVowels("my_perfEct_secret"));
        assertEquals("4bcd3fgh1jklmn0pqrst2vwxyz4bcd3fgh1jklmn0pqrst2vwxyz", PasswordImprover.replaceVowels("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"));
        assertEquals("nnvwls", PasswordImprover.replaceVowels("nnvwls"));
        assertEquals("40321", PasswordImprover.replaceVowels("aoeui"));
    }

    @Test
    public void testIsLetter() {
        assertTrue(PasswordImprover.isLetter('a'));
        assertTrue(PasswordImprover.isLetter('m'));
        assertTrue(PasswordImprover.isLetter('k'));
        assertTrue(PasswordImprover.isLetter('A'));
        assertTrue(PasswordImprover.isLetter('T'));
        assertTrue(PasswordImprover.isLetter('Q'));
        assertTrue(PasswordImprover.isLetter('z'));
        assertTrue(PasswordImprover.isLetter('Z'));
        assertFalse(PasswordImprover.isLetter('0'));
        assertFalse(PasswordImprover.isLetter('3'));
        assertFalse(PasswordImprover.isLetter('9'));
        assertFalse(PasswordImprover.isLetter(' '));
        assertFalse(PasswordImprover.isLetter('!'));
        assertFalse(PasswordImprover.isLetter('@'));
    }

    @Test
    public void testReplaceWithSpecialCharacters() {
        assertEquals("p4s!@0r#", PasswordImprover.replaceWithSpecialCharacters("p4ssw0rd"));
        assertEquals("P4s!@0r#", PasswordImprover.replaceWithSpecialCharacters("P4ssw0rD"));
        assertEquals("w34k_p4s!@0r#", PasswordImprover.replaceWithSpecialCharacters("w34k_p4ssw0rd"));
        assertEquals("w34k_p4s!@0R#", PasswordImprover.replaceWithSpecialCharacters("w34k_p4sSw0Rd"));
        assertEquals("20c_p4s!@0r#", PasswordImprover.replaceWithSpecialCharacters("20c_p4ssw0rd"));
        assertEquals("m!_p3r@3c#_s3c$3t", PasswordImprover.replaceWithSpecialCharacters("my_p3rf3ct_s3cr3t"));
        assertEquals("t!1s@02l#$34p4s%^0r&", PasswordImprover.replaceWithSpecialCharacters("th1sc02ldb34p4ssw0rd"));
        assertEquals("4b!@3f#$1j%^&*0p()_+2v-=[]4b{}3f|;1j:',.0p<>/?2v!@#$", PasswordImprover.replaceWithSpecialCharacters("4bcd3fgh1jklmn0pqrst2vwxyz4bcd3fgh1jklmn0pqrst2vwxyz"));
        assertEquals("3n!0y@#1s$4C", PasswordImprover.replaceWithSpecialCharacters("3nj0yth1sP4C"));
        assertEquals("1c4n!0t@41t#0s33t$3P%", PasswordImprover.replaceWithSpecialCharacters("1c4nn0tw41tt0s33th3PR"));
    }

    @Test
    public void testImprovePassword() {
        assertEquals("p4s!@0r#", PasswordImprover.improvePassword("password"));
        assertEquals("P4s!@0r#", PasswordImprover.improvePassword("PassworD"));
        assertEquals("w34k_p4s!@0r#", PasswordImprover.improvePassword("weak_password"));
        assertEquals("w34k_p4s!@0r#", PasswordImprover.improvePassword("wEAk_pAssWOrd"));
        assertEquals("20c_p4s!@0r#", PasswordImprover.improvePassword("uoc_password"));
        assertEquals("m!_p3r@3c#_s3c$3t", PasswordImprover.improvePassword("my_perfEct_secret"));
        assertEquals("4b!@3f#$1j%^&*0p()_+2v-=[]4b{}3f|;1j:',.0p<>/?2v!@#$", PasswordImprover.improvePassword("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"));
        assertEquals("4b!@3f#$1j%^&*0p()_+2v-=[]4b{}3f|;1j:',.0p<>/?2v!@#$", PasswordImprover.improvePassword(" abcdefg hijkl     mnopqrstuvwx yzabcdefghi jklmnopq rstuvwxyz "));
        assertEquals("s!@0n#3s$%4s^&0r*3v3r", PasswordImprover.improvePassword("strongest password ever"));
        assertEquals("3n!0y@#1s$4C", PasswordImprover.improvePassword("Enjoy this PAC"));
        assertEquals("1c4n!0t@41t#0s33t$3P%", PasswordImprover.improvePassword("I cannot wait to see the PR"));
    }
}
