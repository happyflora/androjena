/*
 * (c) Copyright 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.shared.uuid;

import junit.framework.TestCase;

public class TestUUID extends TestCase
{
    
    public void testNilUUID1()
    {
        JenaUUID u = JenaUUID.nil();
        assertTrue(u.getVariant() == 0) ;
        assertTrue(u.getVersion() == 0) ;
        assertTrue(u == UUID_nil.getNil()) ;
    }

    public void testNilUUID2()
    {
        JenaUUID u = JenaUUID.nil();
        assertTrue(u.isNil()) ;
    }
    
    public void testNilUUID3()
    {
        JenaUUID u = JenaUUID.nil();
        String s = u.asString() ;
        assertEquals(s, UUID_nil.getNilString()) ;
    }
    
    public void testTime1()
    {
        JenaUUID u = JenaUUID.generate() ;
        assertTrue(u.getVersion() == UUID_V1.version) ; 
        assertTrue(u.getVariant() == UUID_V1.variant) ;
    }
    
    public void testTime2()
    {
        JenaUUID u = JenaUUID.generate() ;
        check(u) ;
    }

    public void testTime3()
    {
        UUID_V1 u1 = new UUID_V1_Gen().generateV1() ;
        UUID_V1 u2 = UUID_V1_Gen.generate(u1.getVersion(), u1.getVariant(), u1.getTimestamp(), u1.getClockSequence(), u1.getNode()) ;

        assertEquals(u1.getVersion(),        u2.getVersion() ) ;
        assertEquals(u1.getVariant(),        u2.getVariant() ) ;
        assertEquals(u1.getTimestamp(),      u2.getTimestamp() ) ;
        assertEquals(u1.getClockSequence(),  u2.getClockSequence() ) ;
        assertEquals(u1.getNode(),           u2.getNode() ) ;
    }

    public void testTime4()
    {
        UUID_V1 u1 = new UUID_V1_Gen().generateV1() ;
        UUID_V1 u2 = UUID_V1_Gen.generate(u1.getVersion(), u1.getVariant(), u1.getTimestamp(), u1.getClockSequence(), u1.getNode()) ;
        assertEquals(u1, u2) ;
        assertEquals(u1.asString(), u2.asString()) ;
    }

    
    public void testTime5()
    {
        UUID_V1 u1 = new UUID_V1_Gen().generateV1() ;
        UUID_V1 u2 = UUID_V1_Gen.generate(u1.getVersion(), u1.getVariant(), u1.getTimestamp(), u1.getClockSequence(), u1.getNode()) ;
        assertEquals(u1.asString(), u2.asString()) ;
    }

    public void testTime6()
    {
        JenaUUID u1 = JenaUUID.generate() ;
        JenaUUID u2 = JenaUUID.generate() ;
        assertFalse(u1.equals(u2)) ;
    }
    
    UUIDFactory randomFactory = new UUID_V4_Gen() ;
    
    public void testRandom1()
    {
        JenaUUID u = randomFactory.generate() ;
        assertEquals(u.getVersion(), UUID_V4.version) ;
        assertEquals(u.getVariant(), UUID_V4.variant) ;
    }
    

    public void testRandom2()
    {
        JenaUUID u = randomFactory.generate() ;
        check(u) ;
    }
    
    public void testRandom3()
    {
        JenaUUID u = randomFactory.generate() ;
        check(u.asString()) ;
    }
    
    public void testRandom4()
    {
        JenaUUID u1 = randomFactory.generate() ;
        JenaUUID u2 = randomFactory.generate() ;
        assertFalse(u1.equals(u2)) ;
    }
    
    public void testEquals1()
    {
        JenaUUID u1 = JenaUUID.generate() ;
        JenaUUID u2 = JenaUUID.parse(u1.asString()) ;
        assertNotSame(u1, u2) ;
        assertEquals(u1, u2) ;
        JenaUUID u3 = JenaUUID.generate() ;
        assertFalse(u1.equals(u3)) ;
        assertFalse(u3.equals(u1)) ;
        assertFalse(u2.equals(u3)) ;
        assertFalse(u3.equals(u2)) ;
    }
    
    public void testEquals2()
    {
        JenaUUID u1 = randomFactory.generate() ;
        JenaUUID u2 = JenaUUID.parse(u1.asString()) ;
        assertNotSame(u1, u2) ;
        assertEquals(u1, u2) ;
        JenaUUID u3 = randomFactory.generate() ;
        assertFalse(u1.equals(u3)) ;
        assertFalse(u3.equals(u1)) ;
        assertFalse(u2.equals(u3)) ;
        assertFalse(u3.equals(u2)) ;
    }
    
    public void testHash1()
    {
        JenaUUID u1 = JenaUUID.generate() ;
        JenaUUID u2 = JenaUUID.parse(u1.asString()) ;
        assertNotSame(u1, u2) ;
        assertEquals(u1.hashCode(), u2.hashCode()) ;
        JenaUUID u3 = JenaUUID.generate() ;
        // Time/increment based so should be different
        assertFalse(u1.hashCode() == u3.hashCode()) ;
    }
    
    public void testHash2()
    {
        JenaUUID u1 = randomFactory.generate() ;
        JenaUUID u2 = JenaUUID.parse(u1.asString()) ;
        assertNotSame(u1, u2) ;
        assertEquals(u1.hashCode(), u2.hashCode()) ;
    }
    
    public void testMisc1()
    {
        check("8609C81E-EE1F-4D5A-B202-3EB13AD01823") ;
        check("uuid:DB77450D-9FA8-45D4-A7BC-04411D14E384") ;
        check("UUID:C0B9FE13-179F-413D-8A5B-5004DB8E5BB2") ;
        check("urn:8609C81E-EE1F-4D5A-B202-3EB13AD01823") ;
        check("urn:uuid:70A80F61-77BC-4821-A5E2-2A406ACC35DD") ;
    }

    private void check(String uuidString)
    {
        JenaUUID uuid = JenaUUID.parse(uuidString) ;
        //assertFalse(u.equals(JenaUUID.nil())) ;
        String s2 = uuid.asString() ;
        
        String s = uuidString ;
        if ( s.matches("[uU][rR][nN]:") )
            s = s.substring(4) ;
        if ( s.startsWith("[uU][uU][iI][dD]:") )
            s = s.substring(5) ;
        
        assertTrue(uuidString.equalsIgnoreCase(s)) ;
    }
    
    private void check(JenaUUID uuid)
    {
        String s = uuid.asString() ;
        if ( uuid.isNil() )
        {
            assertEquals(JenaUUID.strNil(), s) ;
            return ;
        }
        JenaUUID uuid2 = JenaUUID.parse(s) ;
        assertTrue(uuid2.equals(uuid)) ;
    }
}

/*
 * (c) Copyright 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */