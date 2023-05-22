package com.example.myapplication.databasecongthuc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.model.CongThuc;

import java.sql.Blob;

public class DatabaseCTnauan extends SQLiteOpenHelper {

    //database name
    private static String DATABASE_NAME ="congthuc";

    //bien bang noi dung cong thuc
    private static String TABLE_CT = "congthucnauan";
    private static String ID_CT = "idcongthuc";
    private static String TEN_CT = "tieude";
    private static String NOI_DUNG_CT = "noidung";
    private static String IMAGE_CT = "anh";
    private static String SOLUOTXEM="slx";

    private static int VERSON=1;

    //context
    private Context context;
    //luu cau lenh them cong thuc
    /*private String SQLQuery ="CREATE TABLE"+ TABLE_CT +"("+ ID_CT+ "integer primary key AUTOINCREMENT"
            + TEN_CT + "TEXT UNIQUE"
            +NOI_DUNG_CT + "TEXT"
            + IMAGE_CT + "TEXT";*/
    private String SQLQuery = "CREATE TABLE "+ TABLE_CT +" ( "+ID_CT+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TEN_CT+" TEXT UNIQUE, "
            +NOI_DUNG_CT+" TEXT, "
            +IMAGE_CT+" BLOB,"
            +SOLUOTXEM+"TEXT) ";
    /*private String SQLQuery = "CREATE TABLE" + TABLE_CT +"("+ ID_CT + "integer primary key AUTOINCREMENT,"
            +TEN_CT +"TEXT UNIQUE"
            +NOI_DUNG_CT+"TEXT"
            +IMAGE_CT+"TEXT";*/
    private String SQLQuery1="INSERT INTO congthucnauan VALUES(null,'Cách làm món dưa rau muống','Rau muống là loại rau thông dụng, có thể chế biến thành nhiều món ăn từ giản dị như luộc, nấu canh, xào đến cầu kỳ hơn một chút như nộm, trộn gỏi… đều rất ngon miệng. \n" +
            "Có một món làm từ rau muống đơn giản, hấp dẫn mà nhiều bà nội trợ chưa để ý đến, đó là món dưa rau muống. Nguyên liệu gồm rau muống là chủ đạo, các thành phần kèm theo là đường, dấm, muối, ớt, tỏi.Cách làm món này rất dễ, ai cũng có thể làm được.\n" +
            "\n" +
            "- Rau muống chọn loại có cọng to và rỗng nhặt bỏ lá, chỉ lấy cọng, rửa sạch, nhúng tái qua nước sôi rồi thả ngay vào bát nước đun sôi để nguội, vớt ra cho vào rổ/rá sạch để ráo.\n" +
            "\n" +
            "- Xếp các cọng rau thẳng hàng với nhau, cắt thành từng đoạn dài khoảng 2-3 đốt ngón tay.\n" +
            "\n" +
            "- Pha nước đun sôi để nguội với đường, dấm, muối thành hỗn hợp nước chua mặn ngọt vừa miệng, cho thêm tỏi để nguyên nhánh và ớt để cả quả hoặc băm nhỏ.\n" +
            "\n" +
            "- Gắp toàn bộ rau muống đặt vào thố thủy tinh hoặc men, đổ nước vừa pha cho ngập rau, đóng nắp lại.\n" +
            "\n" +
            "Một, hai ngày sau, lấy dưa ra ăn, cọng dưa màu vàng, giòn, chua dịu, ngòn ngọt, cay cay.\n" +
            "\n" +
            "Bữa cơm có món dưa rau muống ăn kèm với thịt kho tàu hoặc thịt ba chỉ luộc hoặc đậu rán… thật là hấp dẫn.','nomraumuong.jpg','0')";
    // create table
    private String SQLQuery2= "INSERT INTO congthucnauan VALUES(null, 'Trà cam gừng','I. Nguyên liệu:\n" +
            "\n" +
            "4 túi trà nhúng\n" +
            "Nước sôi\n" +
            "½ quả cam\n" +
            "1 nhánh gừng\n" +
            "4 thìa đường\n" +
            "Vài lát chanh\n" +
            "II. Cách làm:\n" +
            "\n" +
            "- Cho các túi trà vào bình nước sôi, để 5 phút cho ngấm.\n" +
            "\n" +
            "- Cam cắt lấy 4 lát tròn mỏng (để cả vỏ). Gừng thái mỏng thành 8 lát\n" +
            "\n" +
            "- Cho các lát cam, gừng, chanh và đường vào 4 cốc.\n" +
            "\n" +
            "- Rót trà vào 4 cốc đó, nguấy đều cho tan đường.\n" +
            "\n" +
            "- Trà gừng cam này rất thích hợp trong những ngày trời trở lạnh, nó sẽ giúp bạn thấy ấm lại nhanh chóng mỗi khi đi đâu về, đồng thời giảm ho và ngừa cảm lạnh.','tracamgung.jpg','0')";
    private String SQLQuery3 = "INSERT INTO congthucnauan VALUES(null, 'Sườn xào chua ngọt','Nguyên liệu làm Sườn xào chua ngọt\n" +
            " Sườn heo non 500 gr\n" +
            " Ớt chuông 3 trái\n" +
            "(mỗi loại một màu)\n" +
            " Hành tây nhỏ 1 củ\n" +
            " Hành 2 củ\n" +
            " Tỏi 1 củ\n" +
            " Ớt 3 trái\n" +
            " Ngò 1 muỗng canh\n" +
            " Giấm 3 muỗng canh\n" +
            " Nước mắm 2 muỗng canh\n" +
            " Dầu ăn 1 ít\n" +
            " Bột năng 1 ít\n" +
            " Gia vị thông dụng 1 ít\n" +
            "(muối/tiêu/hạt nêm/đường)\n" +
            "Dụng cụ thực hiện\n" +
            "Cách chọn mua ớt chuông tươi ngon\n" +
            "Để chọn mua được những trái ớt chuông ngon, bạn nên chọn những quả có bề mặt da bóng, trơn, dày, vẫn còn cuống dính chặt vào thân, cầm lên thấy nặng tay và có màu sắc sáng. Đây sẽ là những quả ớt chuông tươi ngon và giàu chất dinh dưỡng.\n" +
            "Không chọn mua những quả ớt chuông mềm nhũn, vỏ có những vết thâm, xỉn màu và nứt nẻ vì đây là những quả đã hư và không còn nhiều dưỡng chất\n"+
            "1.Cách chế biến Sườn xào chua ngọt\n" +
            "1\n" +
            "Sơ chế sườn non\n" +
            "Để sườn non sạch và hết mùi hôi bạn rửa sạch với nước, sau đó bắc 1 cái nồi lên bếp cho vào 1 chút muối. Khi nước sôi, cho sườn vào chần sơ khoảng 3 - 5 phút.\n" +
            "\n" +
            "Vớt sườn non ra tô cho ráo nước, sau đó ướp vào sườn với 2 muỗng cà phê nước mắm, 1 muỗng cà phê đường, 1 muỗng cà phê hạt nêm trong khoảng 15 phút.\n" +
            "2.Chiên sườn\n" +
            "Sau khi sườn thấm gia vị, bạn lăn sườn qua một lớp bột năng. Tiếp theo, cho dầu vào một chiếc chảo và bắc lên bếp. Khi dầu nóng, cho sườn vào chiên tới khi vàng đều, sau đó gắp ra dĩa có lót giấy thấm dầu.\n"+
            "3\n" +
            "3.Sơ chế ớt chuông và hành tây\n" +
            "Cắt nhỏ ớt chuông và hành tây thành miếng vừa ăn. Tiếp theo pha sốt chua ngọt gồm: 3 muỗng canh giấm, 1 muỗng canh đường, 2 muỗng canh nước mắm, 2 tép tỏi băm, 1 trái ớt băm (khẩu vị có thể tăng giảm theo ý thích của bạn nhé), sau đó khuấy đều.\n"+
            "Xào sườn chua ngọt\n" +
            "Bắc 1 cái chảo khác lên bếp, cho vào một ít dầu và cho 1 ít hành tím băm vào xào cho thơm. Tiếp đến, cho ớt chuông vào xào.\n" +
            "\n" +
            "Rồi cho sườn vào xào chung. Khi ớt chuông sắp chín, bạn cho hành tây và sốt chua ngọt vào xào. Cuối cùng hòa 1 muỗng cà phê bột năng vào một tí nước rồi cho vào sườn xào tới khi sệt lại là đạt.','E:\\MyApplication\\app\\src\\main\\res\\drawable\\suonxaochuangot.jpg','0') ";
    private String SQLQuery4="INSERT INTO congthucnauan VALUES(null,'Cháo sườn heo','Nguyên liệu làm Cháo sườn heoCho 4 người\n" +
            " Sườn non 300 gr\n" +
            " Gạo 300 gr\n" +
            " Hành tím 3 củ\n" +
            " Muối/Hạt nêm 1 ít.\n"+
            "1.Sơ chế nguyên liệu\n" +
            "Sườn non sau khi mua về bạn rửa sạch máu.\n" +
            "\n" +
            "Nấu sôi 500ml nước, cho sườn non vào trụng, để khoảng 2 phút khi nước sôi lại thì nhắc xuống. Đem sườn đi rửa sạch dưới vòi nước lạnh rồi cho lại vào một chiếc nồi sạch.\n" +
            "\n" +
            "Gạo tẻ vo sạch sau đó đổ vào 300ml nước, ngâm từ 4 tiếng đến qua đêm cho gạo nở. Cho cả gạo và nước ngâm vào máy xay và ấn xay ở mức nhỏ cho đến khi hạt gạo nhỏ nát li ti.\n" +
            "\n" +
            "Hành tím bóc vỏ, cắt mỏng rồi mang đi phi vàng thơm để làm hành phi. Hành lá, ngò rí rửa sạch cắt nhỏ.\n"+
            "2.Nấu cháo\n" +
            "Cho sườn heo đã sơ chế vào nồi cùng với 1.5 lít nước và 1/2 muỗng cà phê muối và hầm 45 phút đến 1 tiếng với lửa vừa cho sườn ra nước ngọt.\n" +
            "\n" +
            "Sau đó gắp sườn ra, xé thịt thành những sợi nhỏ, bỏ xương.\n" +
            "\n" +
            "Để có phần nước dùng không lẫn xương vụn, nước trong thì bạn cho nước dùng đã nấu qua rây lọc rồi cho trở lại vào nồi.\n" +
            "\n" +
            "Tiếp theo bạn cho hết phần gạo đã xay vào nồi nước dùng, nấu lửa vừa đến khi cháo sôi (khoảng 5 - 7 phút) thì hạ lửa nhỏ, đun liu riu trong khoảng 15 phút cho cháo mềm. Để tránh cháo bị dính vào đấy nồi, trong lúc nấu cháo nhớ khuấy thường xuyên nhé.\n" +
            "\n" +
            "Khi cháo đã mềm thêm thịt sườn đã xé vào nồi cháo, nêm 1 muỗng cà phê muối và 2 muỗng cà phê hạt nêm. Cân chỉnh lại lượng gia vị cho vừa khẩu vị, khuấy đều lên rồi tắt bếp.\n"+
            "3.Thành phẩm\n" +
            "Cho cháo ra tô thêm hành ngò cắt nhuyễn, hành phi và 1 ít tiêu xay cho dậy hương thơm và thưởng thức thôi. Cháo sườn nóng hổi, mềm mịn, thịt sườn thơm ngọt, mềm tan rất phù hợp cho người già, trẻ nhỏ dùng trong những ngày trời se lạnh đó nha.','E:\\MyApplication\\app\\src\\main\\res\\drawable\\chaosuonheo.jpg','0') ";
    private String SQLQuery5="INSERT INTO congthucnauan VALUES(null,'Cách làm bánh chuối chiên giòn','Nguyên liệu làm Bánh chuối chiên bột gạo truyền thốngCho 3 người\n" +
            " Chuối sứ chín 10 trái\n" +
            " Bột gạo 100 gr\n" +
            " Bột mì 50 gr\n" +
            " Vani 5 gr\n" +
            "(2 ống)\n"+
            "1.Pha bột\n" +
            "Bạn dùng một cái tô lớn cho bột mì và bột gạo vào trộn đều. Rót từ từ nước lọc vào tô bột, vừa rót vừa khuấy đều cho đến khi có hỗn hợp bột sền sệt là được.\n" +
            "\n" +
            "Tiếp theo bạn cho vào hỗn hợp bột 1/2 muỗng cà phê muối, 1 muỗng canh đường, 2 ống vani khuấy đều. Sau đó bạn để vào chỗ thoáng để bột nghỉ 30 phút. Trong thời gian chờ đợi bạn tiến hành sơ chế chuối.\n"+
            "2.Sơ chế chuối\n" +
            "Chuối sứ bạn bóc vỏ, quả lớn cắt làm 2, quả nhỏ để nguyên, sau đó cho chuối vào bọc ni lông sạch, dùng dao hoặc cây lăn bột ép nhẹ cho chuối hơi bẹp ra. Bạn không ép mạnh tay làm chuối bị nát không chiên được.\n"+
            "3.Chiên bánh\n" +
            "Bạn đặt chảo dầu sâu lòng lên bếp, làm nóng chảo sau đó cho dầu ăn và chảo sao cho khi chiên dầu ngập miếng chuối.\n" +
            "\n" +
            "Đun nóng dầu ăn sau đó gắp miếng chuối nhúng vào bột sao cho bột phủ đều bên ngoài miếng chuối. Bạn gắp miếng chuối nhấc lên cao, đợi một chút để bột thừa rớt lại vào tô rồi mới bỏ vào chảo chiên.\n" +
            "\n" +
            "Chiên bánh với lửa nhỏ đến khi thấy bánh chuyển màu vàng, bạn vớt bánh ra để vào đĩa có lót giấy thấm dầu, bánh được thấm bớt lượng dầu dư sẽ giòn lâu hơn và ăn đỡ ngán hơn.\n" +
            "Thành phẩm\n" +
            "Sau khi bạn chiên hết chuối và bánh thấm bớt dầu, lấy bánh ra đĩa là có thể thưởng thức rồi. Bánh ăn ngon nhất khi còn nóng nhé!','E:\\MyApplication\\app\\src\\main\\res\\drawable\\banhchuoi.jpg','0') ";
    private String qr4="INSERT INTO congthucnauan VALUES(null,'Chân gà xả ớt','Ngâm chân gà vừa đủ','khaivi.png')";

    public DatabaseCTnauan(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //thuc hien cac cau lenh truy van khong tra ve kqua
        db.execSQL(SQLQuery);
        db.execSQL(SQLQuery1);
        db.execSQL(SQLQuery2);
        db.execSQL(SQLQuery3);
        db.execSQL(SQLQuery4);
        db.execSQL(SQLQuery5);
        //db.execSQL(qr4);
        //db.execSQL(SQLQuery2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getData(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT *  FROM "+TABLE_CT,null);
        return res;
    }
    /*public Cursor getDataTopp1(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor rs = db.rawQuery("SELECT * FROM"+ TABLE_CT+"ORDER BY"+ID_CT+ "DESC LIMIT 1", null);
        return rs;
    }*/

    /*public void AddCT(CongThuc congThuc){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TEN_CT, congThuc.getTenCT());
        values.put(NOI_DUNG_CT, congThuc.getNoiDung());

        values.put(String.valueOf(IMAGE_CT),congThuc.getAnh());

        db.insert(TABLE_CT, null,values);
        db.close();
        //Log.e("add ct", "Ct");
    }*/
    public void AddCT(CongThuc congThuc){
        SQLiteDatabase db = getWritableDatabase();
        String sql= "INSERT INTO congthucnauan VALUES(null, ?, ?, ?) ";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,congThuc.getTenCT());
        statement.bindString(2,congThuc.getNoiDung());
        statement.bindBlob(3,congThuc.getAnh());

        statement.executeInsert();
    }

    //xoa ct
    public int Delete(int i){
        SQLiteDatabase db = this.getReadableDatabase();

        int res = db.delete("congthucnauan",ID_CT+" = "+i,null);
        return res;

    }
    //cap nhat
    public int update(CongThuc congThuc){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEN_CT, congThuc.getTenCT());
        values.put(NOI_DUNG_CT, congThuc.getNoiDung());
        values.put(IMAGE_CT, congThuc.getAnh());
        int res = db.update("congthucnauan", values, ID_CT+"=?", new String[]{congThuc.getID() + ""});
        return res;
    }

    public void slx(int soluotxem, int id){
        SQLiteDatabase database= getWritableDatabase();
        database.execSQL("UPDATE"+TABLE_CT+"set"+SOLUOTXEM+"="+soluotxem+"where"+ID_CT+"="+id);
        database.close();
    }

}
