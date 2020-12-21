import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.DeleteValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.commands.kv.UpdateValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;

public class TasteOfRiak {
    public static class Book {
        public String title;
        public String author;
        public String body;
        public String isbn;
        public Integer copiesOwned;

        @Override
        public String toString() {
            return "{title:" + this.title + ",author:" + this.author + ",body:" + this.body + ",isbn:" + this.isbn + ",copiesOwned:" + this.copiesOwned + "}";
        }
    }

    public static class BookUpdate extends UpdateValue.Update<Book> {
        private final Book update;

        public BookUpdate(Book update) {
            this.update = update;
        }

        @Override
        public Book apply(Book t) {
            if (t == null) {
                t = new Book();
            }

            t.author = update.author;
            t.body = update.body;
            t.copiesOwned = update.copiesOwned;
            t.isbn = update.isbn;
            t.title = update.title;

            return t;
        }
    }

    public static void main(String[] args) {
        try {
            RiakClient client = RiakClient.newClient("8098", "127.0.0.1");
            Namespace booksBucket = new Namespace("books");
            Location mobyDickLocation = new Location(booksBucket, "moby_dick");

            Book mobyDick = new Book();
            mobyDick.title = "Moby Dick";
            mobyDick.author = "Herman Melville";
            mobyDick.body = "Call me Ishmael. Some years ago...";
            mobyDick.isbn = "1111979723";
            mobyDick.copiesOwned = 3;

            StoreValue storeBookOp = new StoreValue.Builder(mobyDick)
                    .withLocation(mobyDickLocation)
                    .build();
            client.execute(storeBookOp);
            System.out.println("Book object successfully created");

            printBook(client, mobyDickLocation);

            mobyDick.copiesOwned = 5;
            BookUpdate updatedBook = new BookUpdate(mobyDick);
            UpdateValue updateValue = new UpdateValue.Builder(mobyDickLocation)
                    .withUpdate(updatedBook).build();
            client.execute(updateValue);
            System.out.println("Book object successfully updated");

            printBook(client, mobyDickLocation);

            DeleteValue deleteOp = new DeleteValue.Builder(mobyDickLocation)
                    .build();
            client.execute(deleteOp);
            System.out.println("Book object successfully deleted");

            printBook(client, mobyDickLocation);

            client.shutdown();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printBook(RiakClient client, Location mobyDickLocation) throws java.util.concurrent.ExecutionException, InterruptedException {
        FetchValue fetchMobyDickOp = new FetchValue.Builder(mobyDickLocation)
                .build();
        Book fetchedBook = client.execute(fetchMobyDickOp).getValue(Book.class);
        if (fetchedBook != null) {
            System.out.println(fetchedBook);
            System.out.println("Book object successfully fetched");
        } else {
            System.out.println("Book object wasn't fetched");
        }
    }
}