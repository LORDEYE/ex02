package org.zerock.ex02.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.ex02.entity.Memo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class MemoRepositoryTest {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println("#####"+memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDumies(){

        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });

    }
    @Test
    public void testSelect(){
        Long id = 100L;
        //System.out.println(id + " text =" + memoRepository.getOne(id).getMemoText());

        Optional<Memo> result = memoRepository.findById(id);
        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println("###### ="+memo);
        }


        //System.out.println("getOne###### ="+memoRepository.getOne(id));

    }

    @Test
    @Transactional
    public void testSelect2(){
        Long id = 100L;
        //System.out.println(id + " text =" + memoRepository.getOne(id).getMemoText());


        System.out.println("getOne###### ="+memoRepository.getOne(id));

    }
    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(10L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete(){
       // Memo memo = Memo.builder().mno(10L).build();
        try {
            memoRepository.deleteById(100L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);
        System.out.println("--------------------------------------------");
        System.out.println("Total Page : "+result.getTotalPages());
        System.out.println("Total Count : "+result.getTotalElements());
        System.out.println("Page Number : "+result.getNumber());
        System.out.println("Page size : "+result.getSize());
        System.out.println("has next page : "+result.hasNext());
        System.out.println("first page? : "+result.isFirst());
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(1,10,sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void 쿼리메서드(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L,80L);

        for(Memo memo: list){
            System.out.println(memo);
        }
    }
}