import java.util.List;

public class CircularLinkedList {
    class Element {
        public Object value=null;
        private Element next=null;
    }
    private Element header = null;//head node
    /**
     * Initialization chain table
     * */
    public CircularLinkedList() {
        header = new Element();
        header.value=null;
        header.next=header;
    }

    /**
     * Insert chain list
     * */
    void insertList(Object obj) {
        Element element=new Element();
        element.value=obj;
        if(header.next==header){//First insert element
            header.next=element;
            element.next=header;
        }else {
            Element temp = header;
            //Looking for the last element
            while(temp.next!=header) {
                temp=temp.next;
            }
            temp.next=element;
            element.next=header;//The last node refers to the head node
        }
    }

    void insertList(Object obj,int ith) {
        if (ith == 0){
            System.out.println("The location of the list is wrong!");
            return;
        }
        Element element=new Element();
        element.value=obj;
        if(this.size() == 0 || ith == this.size()+1){
            insertList(obj);
        }else if(ith == 1){
            Element temp = header.next;
            header.next = element;
            element.next = temp;
        }else {
            Element ithElement = header.next;
            for(int i=0;i<ith-2;i++){
                ithElement = ithElement.next;
            }
            Element xx = ithElement.next;
            ithElement.next = element;
            element.next = xx;

        }
    }

    /**
     * Delete the ith element in the list
     *
     * */
    void deleteList(Object obj) {
        Element temp =header;
        while(temp.next!=header) {
            if(temp.next.value.equals(obj)) {
                temp.next=temp.next.next;
            }else {
                temp=temp.next;
            }
        }
    }


    /**
     * Get the element in the ith location of the list
     * */
    Element getElement(int i) {
        if(i<=0 || i>size()) {
            System.out.println("The location of the list is wrong!");
            return null;
        } else {
            int count =0;
            Element element = new Element();
            Element temp = header;
            while(temp.next!=header) {
                count++;
                if(count==i) {
                    element.value=temp.next.value;
                }
                temp=temp.next;
            }
            return element;
        }
    }

    /**
     * the size of chain list
     * */
    int size() {
        Element temp = header;
        int size=0;
        while(temp.next!=header) {
            size++;
            temp=temp.next;
        }
        return size;
    }

    /**
     * Determine whether there is a specified element in the list
     * */
    Boolean isContain(Object obj) {
        Element temp =header;
        while(temp.next!=header) {
            if(temp.next.value.equals(obj)) {
                return true;
            }
            temp=temp.next;
        }
        return false;
    }
    /**
     * print chain list
     * */
    void print() {
        System.out.print("printing chain lists: ");
        Element temp =header;
        while(temp.next!=header) {
            temp=temp.next;
            System.out.print(temp.value+"\t");
        }
        System.out.println();
    }

}