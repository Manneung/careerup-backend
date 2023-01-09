package com.manneung.careerup.domain.item.service;

import com.manneung.careerup.domain.file.model.File;
import com.manneung.careerup.domain.file.service.FileService;
import com.manneung.careerup.domain.item.model.Item;
import com.manneung.careerup.domain.item.model.dto.item.GetItemDetailRes;
import com.manneung.careerup.domain.item.repository.ItemRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemServiceTest {

    private FileService fileService;

    private ItemRepository itemRepository;

    private ItemService itemService;



    @Test
    public void testShowItemDetail() {
        fileService = mock(FileService.class);
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository, fileService);
        // Create test data
        int itemIdx = 1;
        List<File> files = new ArrayList<>();
        files.add(new File(1, 1, "활동사진", "file1.jpg"));
        files.add(new File(1, 1,  "활동파일", "file2.jpg"));
        Item item = new Item();
        item.setItemIdx(1);
        item.setMapIdx(2);
        item.setSequence(3);
        item.setCategory("Category");
        item.setTitle("Title");
        item.setInstitution("Institution");
        item.setPeriod("Period");
        item.setAcquisition("Acquisition");
        item.setField("Field");
        item.setRole("Role");
        item.setContent("Content");
        item.setRealization("Realization");

        // Configure mock behavior
        when(fileService.findFilesByItemIdx(itemIdx)).thenReturn(files);
        when(itemRepository.findByItemIdx(itemIdx)).thenReturn(item);

        // Call method under test
        GetItemDetailRes res = itemService.showItemDetail(itemIdx);

        // Verify results
        assertEquals(item.getTitle(), res.getTitle());
        assertEquals(item.getContent(), res.getContent());
        assertEquals(files, res.getFiles());
    }

}