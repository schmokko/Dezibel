package de.dezibel.data;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander Trahe
 */
public class UserTest {
    
    User loggedUser;
    User user1;
    User user2;
    User user3;
    Label label1;
    Label label2;
    News news1;
    Application appl1;
    Application appl2;
    Medium med1;
    Medium med2;
    Playlist play1;
    Playlist play2;
    Comment com1;
    Comment com2;
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        loggedUser = new User("panni@web.de", "Peter", "Pan", "a1b2c3", true);
        loggedUser.setBirthdate(new Date(1912, 11, 24));
        loggedUser.setCity("Greven");
        loggedUser.setCountry("Deutschland");
        
        user1 = new User ("Mark@web.de", "Mark", "Gnom", "123", true);
        user1.setBirthdate(new Date(1987, 1, 4));
        user1.setCity("Münster");
        user1.setCountry("Deutschland");
        
        user2 = new User("ca@web.de", "Valerius", "Constantin", "321", true);
        user2.setBirthdate(new Date(1987, 1, 4));
        user2.setCity("Münster");
        user2.setCountry("Deutschland");
        
        label1 = new Label(user2, "NTM");
        label2 = new Label(user2, "GeilRecords");
        
        news1 = new News("Pizza!", "Bald gibt es Pizza", loggedUser);
        
        appl1 = new Application(true, "Ich kann tolle Musik erstellen", loggedUser, label1);
        appl2 = new Application(false, "Deine Musik ist echt super. Komm zu uns!", loggedUser, label2);
        
        med1 = new Medium("Super Hits", loggedUser, "c:\bla");
        med2 = new Medium("Crappy Hits", loggedUser, "c:\bli");
        
        play1 = new Playlist(med1, "Super List", loggedUser);
        play2 = new Playlist(med2, "Awful List", loggedUser);
        
        com1 = new Comment("Voll toll!", med1, loggedUser);
        com2 = new Comment("Voll der Mist!", med2, loggedUser);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addArtistLabel method, of class User.
     */
    @Test
    public void testAddArtistLabel() {
        loggedUser.addArtistLabel(label1);
        assertTrue(loggedUser.getPublishingLabels().contains(label1));
        assertTrue(label1.getArtists().contains(loggedUser));
    }

    /**
     * Test of removeArtistLabel method, of class User.
     */
    @Test
    public void testRemoveArtistLabel() {
        loggedUser.addArtistLabel(label1);
        loggedUser.addArtistLabel(label2);
        loggedUser.removeArtistLabel(label1);
        assertTrue(loggedUser.getPublishingLabels().contains(label2));
        assertFalse(loggedUser.getPublishingLabels().contains(label1));
        assertTrue(label2.getArtists().contains(loggedUser));
        assertFalse(label1.getArtists().contains(loggedUser));
    }

    /**
     * Test of addManagerLabel method, of class User.
     */
    @Test
    public void testAddManagerLabel() {
        loggedUser.addManagerLabel(label1);
        assertTrue(loggedUser.getManagedLabels().contains(label1));
        assertTrue(label1.getLabelManagers().contains(loggedUser));
    }

    /**
     * Test of removeManagerLabel method, of class User.
     */
    @Test
    public void testRemoveManagerLabel() {
        loggedUser.addManagerLabel(label1);
        loggedUser.addManagerLabel(label2);
        loggedUser.removeManagerLabel(label1);
        assertTrue(loggedUser.getManagedLabels().contains(label2));
        assertFalse(loggedUser.getManagedLabels().contains(label1));
        assertTrue(label2.getLabelManagers().contains(loggedUser));
        assertFalse(label1.getLabelManagers().contains(loggedUser));
    }

    /**
     * Test of addFavoriteLabel method, of class User.
     */
    @Test
    public void testAddFavoriteLabel() {
        loggedUser.addFavoriteLabel(label1);
        assertTrue(loggedUser.getFavoriteLabels().contains(label1));
        assertTrue(label1.getFollowers().contains(loggedUser));
    }

    /**
     * Test of removeFavoriteLabel method, of class User.
     */
    @Test
    public void testRemoveFavoriteLabel() {
        loggedUser.addFavoriteLabel(label1);
        loggedUser.addFavoriteLabel(label2);
        loggedUser.removeFavoriteLabel(label1);
        assertTrue(loggedUser.getFavoriteLabels().contains(label2));
        assertFalse(loggedUser.getFavoriteLabels().contains(label1));
        assertTrue(label2.getFollowers().contains(loggedUser));
        assertFalse(label1.getFollowers().contains(loggedUser));
    }

    /**
     * Test of addFavoriteUser method, of class User.
     */
    @Test
    public void testAddFavoriteUser() {
        loggedUser.addFavoriteUser(user1);
        assertTrue(loggedUser.getFavoriteUsers().contains(user1));
        assertTrue(user1.getFollowers().contains(loggedUser));
    }

    /**
     * Test of removeFavoriteUser method, of class User.
     */
    @Test
    public void testRemoveFavoriteUser() {
        loggedUser.addFavoriteUser(user1);
        loggedUser.addFavoriteUser(user2);
        loggedUser.removeFavoriteUser(user1);
        assertTrue(loggedUser.getFavoriteUsers().contains(user2));
        assertFalse(loggedUser.getFavoriteUsers().contains(user1));
        assertTrue(user2.getFollowers().contains(loggedUser));
        assertFalse(user1.getFollowers().contains(loggedUser));
    }

    /**
     * Test of addFollower method, of class User.
     */
    @Test
    public void testAddFollower() {
        loggedUser.addFollower(user1);
        assertTrue(loggedUser.getFollowers().contains(user1));
        assertTrue(user1.getFavoriteUsers().contains(loggedUser));
    }

    /**
     * Test of removeFollower method, of class User.
     */
    @Test
    public void testRemoveFollower() {
        loggedUser.addFollower(user1);
        loggedUser.addFollower(user2);
        loggedUser.removeFollower(user1);
        assertTrue(loggedUser.getFollowers().contains(user2));
        assertFalse(loggedUser.getFollowers().contains(user1));
        assertTrue(user2.getFavoriteUsers().contains(loggedUser));
        assertFalse(user1.getFavoriteUsers().contains(loggedUser));
    }

    /**
     * Test of addNews method, of class User.
     */
    @Test
    public void testAddNews() {
        loggedUser.addNews(news1);
        assertTrue(loggedUser.getNews().contains(news1));
        assertEquals(loggedUser, news1.getAuthor());
    }

    /**
     * Test of addApplication method, of class User.
     */
    @Test
    public void testAddApplication() {
        loggedUser.addApplication(appl1);
        assertTrue(loggedUser.getApplications().contains(appl1));
        assertEquals(appl1.getLabel(), label1);
    }

    /**
     * Test of removeApplication method, of class User.
     */
    @Test
    public void testRemoveApplication() {
        loggedUser.addApplication(appl1);
        loggedUser.addApplication(appl2);
        loggedUser.removeApplication(appl1);
        assertTrue(loggedUser.getApplications().contains(appl2));
        assertFalse(loggedUser.getApplications().contains(appl1));
        assertTrue(appl2.getLabel().getApplications().get(appl2.getLabel().getApplications().indexOf(appl2)).getUser() == loggedUser);
    }

    /**
     * Test of addCreatedMedium method, of class User.
     */
    @Test
    public void testAddCreatedMedium() {
        loggedUser.addCreatedMedium(med1);
        assertTrue(loggedUser.getCreatedMediums().contains(med1));
        assertTrue(med1.getArtist() == loggedUser);
    }

    /**
     * Test of removeMedium method, of class User.
     */
    @Test
    public void testRemoveMedium() {
        loggedUser.addCreatedMedium(med1);
        loggedUser.addCreatedMedium(med2);
        loggedUser.removeMedium(med1);
        assertTrue(loggedUser.getCreatedMediums().contains(med2));
        assertFalse(med1.isAvailable());
    }

    /**
     * Test of addCreatedPlaylist method, of class User.
     */
    @Test
    public void testAddCreatedPlaylist() {
        loggedUser.addCreatedPlaylist(play1);
        assertTrue(loggedUser.getCreatedPlaylists().contains(play1));
        assertTrue(play1.getCreator() == loggedUser);
    }

    /**
     * Test of removePlaylist method, of class User.
     */
    @Test
    public void testRemovePlaylist() {
        loggedUser.addCreatedPlaylist(play1);
        loggedUser.addCreatedPlaylist(play2);
        loggedUser.removePlaylist(play1);
        assertTrue(loggedUser.getCreatedPlaylists().contains(play2));
        assertFalse(loggedUser.getCreatedPlaylists().contains(play1));
        assertTrue(play2.getCreator()== loggedUser);
        assertFalse(play1.getCreator() == loggedUser);
    }

    /**
     * Test of addCreatedComments method, of class User.
     */
    @Test
    public void testAddCreatedComments() {
        loggedUser.addCreatedComments(com1);
        assertTrue(loggedUser.getCreatedComments().contains(com1));
        assertTrue(com1.getAuthor() == loggedUser);
    }

    /**
     * Test of removeComment method, of class User.
     */
    @Test
    public void testDeleteComment() {
        loggedUser.addCreatedComments(com1);
        loggedUser.addCreatedComments(com2);
        loggedUser.deleteComment(com1);
        assertTrue(loggedUser.getCreatedComments().contains(com2));
        assertFalse(loggedUser.getCreatedComments().contains(com1));
        assertTrue(com2.getAuthor() == loggedUser);
    }

    /**
     * Test of promoteToArtist method, of class User.
     */
    @Test
    public void testPromoteToArtist() {
        loggedUser.promoteToArtist();
        assertTrue(loggedUser.isArtist());
    }

    /**
     * Test of promoteToLabelManager method, of class User.
     */
    @Test
    public void testPromoteToLabelManager() {
        loggedUser.promoteToLabelManager();
        assertTrue(loggedUser.isLabelManager());
    }

    /**
     * Test of promoteToAdmin method, of class User.
     */
    @Test
    public void testPromoteToAdmin() {
        loggedUser.promoteToAdmin();
        assertTrue(loggedUser.isAdmin());
    }

    /**
     * Test of lock method, of class User.
     */
    @Test
    public void testLock() {
        loggedUser.lock();
        assertTrue(loggedUser.isLocked());
    }

    /**
     * Test of lock method, of class User.
     */
    @Test
    public void testLock_String() {
        loggedUser.lock("Du hast Mist gemacht!");
        assertTrue(loggedUser.isLocked());
        assertEquals(loggedUser.getLockText(), "Du hast Mist gemacht!");
    }

    /**
     * Test of unlock method, of class User.
     */
    @Test
    public void testUnlock() {
        loggedUser.unlock();
        assertFalse(loggedUser.isLocked());
    }

    /**
     * Test of getFirstname method, of class User.
     */
    @Test
    public void testGetFirstname() {
        loggedUser.getFirstname();
        assertEquals(loggedUser.getFirstname(), "Peter");
    }

    /**
     * Test of setFirstname method, of class User.
     */
    @Test
    public void testSetFirstname() {
        loggedUser.setFirstname("Rapunzel");
        assertEquals(loggedUser.getFirstname(), "Rapunzel");
    }

    /**
     * Test of setLastname method, of class User.
     */
    @Test
    public void testSetLastname() {
        loggedUser.setLastname("Rapunzel");
        assertEquals(loggedUser.getLastname(), "Rapunzel");
    }

    /**
     * Test of setBirthdate method, of class User.
     */
    @Test
    public void testSetBirthdate() {
        loggedUser.setBirthdate(new Date(1987, 4, 4));
        assertEquals(loggedUser.getBirthdate(), new Date(1987, 4, 4));
    }

    /**
     * Test of setCity method, of class User.
     */
    @Test
    public void testSetCity() {
        loggedUser.setCity("München");
        assertEquals(loggedUser.getCity(), "München");
    }

    /**
     * Test of setCountry method, of class User.
     */
    @Test
    public void testSetCountry() {
        loggedUser.setCountry("Rapunzel");
        assertEquals( "Rapunzel", loggedUser.getCountry());
    }

    /**
     * Test of setIsMale method, of class User.
     */
    @Test
    public void testSetIsMale() {
        loggedUser.setIsMale(false);
        assertFalse(loggedUser.isMale());
    }

    /**
     * Test of setEmail method, of class User.
     */
    @Test
    public void testSetEmail() {
        loggedUser.setEmail("blödi@bla.de");
        assertEquals("blödi@bla.de" ,loggedUser.getEmail());
    }

    /**
     * Test of setPassword method, of class User.
     */
    @Test
    public void testSetPassword() {
        loggedUser.setPassword("abc");
        assertEquals("abc", loggedUser.getPassword());
    }

    /**
     * Test of setDescription method, of class User.
     */
    @Test
    public void testSetDescription() {
        loggedUser.setDescription("Ich bin Peter Pan!");
        assertEquals("Ich bin Peter Pan!", loggedUser.getDescription());
    }
    
}
